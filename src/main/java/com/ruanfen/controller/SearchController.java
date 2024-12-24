package com.ruanfen.controller;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ArticleDoc;
import com.ruanfen.Docs.PatentDoc;
import com.ruanfen.Docs.ProjectDoc;
import com.ruanfen.Docs.ResearcherDoc;
import com.ruanfen.model.Result;
import com.ruanfen.request.SearchField;
import com.ruanfen.request.SearchQueryRequest;
import com.ruanfen.utils.ESCClientUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private RestHighLevelClient client = ESCClientUtil.client();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping("/article/allArticle")
    public Result<List<ArticleDoc>> searchArticleAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("article");

        String redisKey = "article:all";
        List<ArticleDoc> cachedDocs = (List<ArticleDoc>) redisTemplate.opsForValue().get(redisKey);
        if (cachedDocs != null) {
            // 如果缓存存在，直接返回
            return Result.success(cachedDocs);
        }

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchAllQueryBuilder qb = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(qb);

        //SearchRequest搜索请求,并指定要查询的索引
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        List<ArticleDoc> docs = new ArrayList<>();
        for(SearchHit hit : hits){
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }

        redisTemplate.opsForValue().set(redisKey, docs, Duration.ofHours(5));

        return Result.success(docs);
    }

    @GetMapping("/article")
    public Result<List<ArticleDoc>> searchArticleByField(@RequestParam String field, @RequestParam String text) throws IOException {
        // 使用 field 和 text 生成唯一的缓存键
        String redisKey = "article:search:" + field + ":" + text;

        // 1. 查询缓存
        List<ArticleDoc> cachedDocs = (List<ArticleDoc>) redisTemplate.opsForValue().get(redisKey);
        if (cachedDocs != null) {
            // 如果缓存存在，直接返回
            return Result.success(cachedDocs);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("article");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ArticleDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ArticleDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理响应结果
        List<ArticleDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }

        redisTemplate.opsForValue().set(redisKey, docs, Duration.ofMinutes(10));

        return Result.success(docs);

    }


//    @PostMapping("/article/multi")
//    public Result<List<ArticleDoc>> searchArticleByMultiFields(
//            @RequestBody SearchQueryRequest queryRequest) throws IOException {
//
//        // 1. 创建 SearchRequest，指定索引
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("article");
//
//        // 2. 创建 SearchSourceBuilder，用于构建查询条件
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        // 3. 创建 bool 查询，支持多个子查询条件
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        // 4. 根据用户传入的多个字段进行查询
//        String relation = queryRequest.getRelation();
//        for (SearchField searchField : queryRequest.getFieldsAndTexts()) {
//            String fieldName = searchField.getField();
//            String fieldText = searchField.getText();
//            // 根据字段类型决定查询方式
//            String fieldType = ArticleDoc.getFieldType(fieldName);
//            switch (relation){
//                case "and":
//                    if ("text".equals(fieldType)) {
//                        boolQuery.must(QueryBuilders.matchQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else if ("keyword".equals(fieldType)) {
//                        boolQuery.must(QueryBuilders.termQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else {
//                        return Result.error("该字段无法搜索");
//                    }
//                    break;
//                case "or":
//                    if ("text".equals(fieldType)) {
//                        boolQuery.should(QueryBuilders.matchQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else if ("keyword".equals(fieldType)) {
//                        boolQuery.should(QueryBuilders.termQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else {
//                        return Result.error("该字段无法搜索");
//                    }
//                    break;
//                case "not":
//                    if ("text".equals(fieldType)) {
//                        boolQuery.mustNot(QueryBuilders.matchQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else if ("keyword".equals(fieldType)) {
//                        boolQuery.mustNot(QueryBuilders.termQuery(fieldName, fieldText));  // 默认为 should (OR)
//                    } else {
//                        return Result.error("该字段无法搜索");
//                    }
//                    break;
//                default:
//                    return Result.error("传入relation不合法.");
//            }
//        }
//
//        searchSourceBuilder.query(boolQuery);
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        List<ArticleDoc> docs = new ArrayList<>();
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            String jsonStr = hit.getSourceAsString();
//            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
//            docs.add(articleDoc);
//        }
//
//        return Result.success(docs);
//    }

    @PostMapping("/article/cond")
    public Result<List<ArticleDoc>> searchArticleByCondFields(@RequestBody SearchQueryRequest searchQueryRequest) throws IOException{
        String cacheKey = "article:" + searchQueryRequest.generateCacheKey();

        List<ArticleDoc> cachedDocs = (List<ArticleDoc>)redisTemplate.opsForValue().get(cacheKey);
        if (cachedDocs != null) {
            return Result.success(cachedDocs);  // 如果缓存命中，直接返回
        }

        // 1. 创建 SearchRequest，指定索引
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("article");

        // 2. 创建 SearchSourceBuilder，用于构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 3. 创建 bool 查询，支持多个子查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 4. 处理 OR 条件部分：a || b
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getOrFieldsAndTexts()) {
            String fieldName = searchField.getField();
            String fieldText = searchField.getText();

            String fieldType = ArticleDoc.getFieldType(fieldName); // 获取字段类型

            if ("text".equals(fieldType)) {
                orQuery.should(QueryBuilders.matchQuery(fieldName, fieldText));  // OR 查询
            } else if ("keyword".equals(fieldType)) {
                orQuery.should(QueryBuilders.termQuery(fieldName, fieldText));  // OR 查询
            } else {
                return Result.error("该字段无法搜索");
            }
        }

        // 将 OR 查询部分加入主查询
        boolQuery.must(orQuery);

        // 5. 处理 AND 条件部分：c && d && e
        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getAndFieldsAndTexts()) {
            String fieldName = searchField.getField();
            String fieldText = searchField.getText();

            String fieldType = ArticleDoc.getFieldType(fieldName); // 获取字段类型

            if ("text".equals(fieldType)) {
                andQuery.must(QueryBuilders.matchQuery(fieldName, fieldText));  // AND 查询
            } else if ("keyword".equals(fieldType)) {
                andQuery.must(QueryBuilders.termQuery(fieldName, fieldText));  // AND 查询
            } else {
                return Result.error("该字段无法搜索");
            }
        }
        boolQuery.must(andQuery);

        // 6. 设置查询条件
        searchSourceBuilder.query(boolQuery);



        if (searchQueryRequest.getOrderField() != null && !searchQueryRequest.getOrderField().isEmpty()) {
            String orderField = searchQueryRequest.getOrderField();
            SortOrder sortOrder = searchQueryRequest.getDesc() == 1 ? SortOrder.DESC : SortOrder.ASC;
            searchSourceBuilder.sort(orderField, sortOrder); // 设置排序
        }

        searchRequest.source(searchSourceBuilder);

        // 7. 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 8. 处理响应结果
        List<ArticleDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }
        redisTemplate.opsForValue().set(cacheKey, docs, Duration.ofMinutes(30));

        return Result.success(docs);
    }

    @GetMapping("/article/page")
    public Result<List<ArticleDoc>> pageSearchArticleByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException {
        // 缓存键生成
        String cacheKey =  "article:search:" + field + ":" + text + ":page:" + page + ":size:" + pageSize;

        // 先尝试从缓存中获取数据
        List<ArticleDoc> cachedDocs = (List<ArticleDoc>)redisTemplate.opsForValue().get(cacheKey);

        // 如果缓存中有数据，直接返回
        if (cachedDocs != null) {
            return Result.success(cachedDocs);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("article");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ArticleDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ArticleDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }
        searchSourceBuilder.from((page-1) * pageSize); // 起始位置
        searchSourceBuilder.size(pageSize); // 每页显示数量

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理响应结果
        List<ArticleDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }
        // 将查询结果存入缓存，并设置缓存过期时间（例如1小时）
        redisTemplate.opsForValue().set(cacheKey, docs, Duration.ofMinutes(30));

        return Result.success(docs);

    }

    @GetMapping("/article/doc")
    public Result<ArticleDoc> searchArticleById(@RequestParam int articleId) throws IOException {
        String redisKey = "article:doc:" + articleId;

        ArticleDoc cachedArticleDoc = (ArticleDoc) redisTemplate.opsForValue().get(redisKey);
        if (cachedArticleDoc != null) {
            return Result.success(cachedArticleDoc);
        }

        // 1.准备Request
        GetRequest request = new GetRequest("article", String.valueOf(articleId));
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            // 3.解析响应结果
            String json = response.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(json, ArticleDoc.class);

            redisTemplate.opsForValue().set(redisKey, articleDoc, Duration.ofMinutes(5));
            return Result.success(articleDoc);
        }else {
            return Result.error();
        }
    }

    @GetMapping("/researcher/allResearcher")
    public Result<List<ResearcherDoc>> searchResearcherAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("researcher");

        String redisKey = "researcher:all";
        List<ResearcherDoc> cachedDocs = (List<ResearcherDoc>) redisTemplate.opsForValue().get(redisKey);
        if (cachedDocs != null) {
            // 如果缓存存在，直接返回
            return Result.success(cachedDocs);
        }

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //SearchRequest搜索请求,并指定要查询的索引
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        List<ResearcherDoc> docs = new ArrayList<>();
        for(SearchHit hit : hits){
            String jsonStr = hit.getSourceAsString();
            ResearcherDoc doc = JSON.parseObject(jsonStr, ResearcherDoc.class);
            docs.add(doc);
        }
        redisTemplate.opsForValue().set(redisKey, docs, Duration.ofHours(5));
        return Result.success(docs);
    }

    @GetMapping("/researcher")
    public Result<List<ResearcherDoc>> searchResearcherByField(@RequestParam String field, @RequestParam String text) throws IOException{
        String redisKey = "researcher:search:" + field + ":" + text;

        // 1. 查询缓存
        List<ResearcherDoc> cachedDocs = (List<ResearcherDoc>) redisTemplate.opsForValue().get(redisKey);
        if (cachedDocs != null) {
            // 如果缓存存在，直接返回
            return Result.success(cachedDocs);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("researcher");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ResearcherDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ResearcherDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理响应结果
        List<ResearcherDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ResearcherDoc researcherDoc = JSON.parseObject(jsonStr, ResearcherDoc.class);
            docs.add(researcherDoc);
        }
        redisTemplate.opsForValue().set(redisKey, docs, Duration.ofMinutes(10));
        return Result.success(docs);
    }


    @PostMapping("/researcher/cond")
    public Result<List<ResearcherDoc>> searchResearcherByCondFields(@RequestBody SearchQueryRequest searchQueryRequest) throws IOException{
        String cacheKey = "researcher:" + searchQueryRequest.generateCacheKey();

        List<ResearcherDoc> cachedDocs = (List<ResearcherDoc>)redisTemplate.opsForValue().get(cacheKey);
        if (cachedDocs != null) {
            return Result.success(cachedDocs);  // 如果缓存命中，直接返回
        }

        // 1. 创建 SearchRequest，指定索引
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("researcher");

        // 2. 创建 SearchSourceBuilder，用于构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 3. 创建 bool 查询，支持多个子查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 4. 处理 OR 条件部分：a || b
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getOrFieldsAndTexts()) {
            String fieldName = searchField.getField();
            String fieldText = searchField.getText();

            String fieldType = ResearcherDoc.getFieldType(fieldName); // 获取字段类型

            if ("text".equals(fieldType)) {
                orQuery.should(QueryBuilders.matchQuery(fieldName, fieldText));  // OR 查询
            } else if ("keyword".equals(fieldType)) {
                orQuery.should(QueryBuilders.termQuery(fieldName, fieldText));  // OR 查询
            } else {
                return Result.error("该字段无法搜索");
            }
        }

        // 将 OR 查询部分加入主查询
        boolQuery.must(orQuery);

        // 5. 处理 AND 条件部分：c && d && e
        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getAndFieldsAndTexts()) {
            String fieldName = searchField.getField();
            String fieldText = searchField.getText();

            String fieldType = ResearcherDoc.getFieldType(fieldName); // 获取字段类型

            if ("text".equals(fieldType)) {
                andQuery.must(QueryBuilders.matchQuery(fieldName, fieldText));  // AND 查询
            } else if ("keyword".equals(fieldType)) {
                andQuery.must(QueryBuilders.termQuery(fieldName, fieldText));  // AND 查询
            } else {
                return Result.error("该字段无法搜索");
            }
        }
        boolQuery.must(andQuery);

        // 6. 设置查询条件
        searchSourceBuilder.query(boolQuery);

        searchSourceBuilder.size(10);  // 限制返回 10 条数据

        if (searchQueryRequest.getOrderField() != null && !searchQueryRequest.getOrderField().isEmpty()) {
            String orderField = searchQueryRequest.getOrderField();
            SortOrder sortOrder = searchQueryRequest.getDesc() == 1 ? SortOrder.DESC : SortOrder.ASC;
            searchSourceBuilder.sort(orderField, sortOrder); // 设置排序
        }

        searchRequest.source(searchSourceBuilder);

        // 7. 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 8. 处理响应结果
        List<ResearcherDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ResearcherDoc doc = JSON.parseObject(jsonStr, ResearcherDoc.class);
            docs.add(doc);
        }
        redisTemplate.opsForValue().set(cacheKey, docs, Duration.ofMinutes(30));

        return Result.success(docs);
    }

    @GetMapping("/researcher/page")
    public Result<List<ResearcherDoc>> pageSearchResearcherByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException{
        // 缓存键生成
        String cacheKey = "researcher:search:" + field + ":" + text + ":page:" + page + ":size:" + pageSize;

        // 先尝试从缓存中获取数据
        List<ResearcherDoc> cachedDocs = (List<ResearcherDoc>)redisTemplate.opsForValue().get(cacheKey);

        // 如果缓存中有数据，直接返回
        if (cachedDocs != null) {
            return Result.success(cachedDocs);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("researcher");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ResearcherDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ResearcherDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }
        searchSourceBuilder.from((page-1) * pageSize); // 起始位置
        searchSourceBuilder.size(pageSize); // 每页显示数量

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        // 处理响应结果
        List<ResearcherDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ResearcherDoc researcherDoc = JSON.parseObject(jsonStr, ResearcherDoc.class);
            docs.add(researcherDoc);
        }

        return Result.success(docs);
    }

    @GetMapping("/researcher/doc")
    public Result<ResearcherDoc> searchResearcherById(@RequestParam int researcherId) throws IOException {
        String redisKey = "researcher:doc:" + researcherId;

        ResearcherDoc cachedArticleDoc = (ResearcherDoc) redisTemplate.opsForValue().get(redisKey);
        if (cachedArticleDoc != null) {
            return Result.success(cachedArticleDoc);
        }
        // 1.准备Request
        GetRequest request = new GetRequest("researcher", String.valueOf(researcherId));
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            // 3.解析响应结果
            String json = response.getSourceAsString();
            ResearcherDoc doc = JSON.parseObject(json, ResearcherDoc.class);

            redisTemplate.opsForValue().set(redisKey, doc, Duration.ofMinutes(5));
            return Result.success(doc);
        }else {
            return Result.error();
        }
    }

    @GetMapping("/patent/allPatent")
    public Result<List<PatentDoc>> searchPatentAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("patent");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //SearchRequest搜索请求,并指定要查询的索引
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        List<PatentDoc> docs = new ArrayList<>();
        for(SearchHit hit : hits){
            String jsonStr = hit.getSourceAsString();
            PatentDoc doc = JSON.parseObject(jsonStr, PatentDoc.class);
            docs.add(doc);
        }

        return Result.success(docs);
    }

    @GetMapping("/patent")
    public Result<List<PatentDoc>> searchPatentByField(@RequestParam String field, @RequestParam String text) throws IOException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("patent");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(PatentDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(PatentDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理响应结果
        List<PatentDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            PatentDoc doc = JSON.parseObject(jsonStr, PatentDoc.class);
            docs.add(doc);
        }
        return Result.success(docs);
    }

    @GetMapping("/patent/page")
    public Result<List<PatentDoc>> pageSearchPatentByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("patent");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(PatentDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(PatentDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }
        searchSourceBuilder.from((page-1) * pageSize); // 起始位置
        searchSourceBuilder.size(pageSize); // 每页显示数量

        searchRequest.source(searchSourceBuilder);


        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        // 处理响应结果
        List<PatentDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            PatentDoc doc = JSON.parseObject(jsonStr, PatentDoc.class);
            docs.add(doc);
        }
        return Result.success(docs);
    }

    @GetMapping("/patent/doc")
    public Result<PatentDoc> searchPatentById(@RequestParam int patentId) throws IOException{
        // 1.准备Request
        GetRequest request = new GetRequest("patent", String.valueOf(patentId));
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            // 3.解析响应结果
            String json = response.getSourceAsString();
            PatentDoc doc = JSON.parseObject(json, PatentDoc.class);
            return Result.success(doc);
        }else {
            return Result.error();
        }
    }

    @GetMapping("/project/allProject")
    public Result<List<ProjectDoc>> searchProjectAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("project");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //SearchRequest搜索请求,并指定要查询的索引
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();

        List<ProjectDoc> docs = new ArrayList<>();
        for(SearchHit hit : hits){
            String jsonStr = hit.getSourceAsString();
            ProjectDoc doc = JSON.parseObject(jsonStr, ProjectDoc.class);
            docs.add(doc);
        }

        return Result.success(docs);
    }

    @GetMapping("/project")
    public Result<List<ProjectDoc>> searchProjectByField(@RequestParam String field, @RequestParam String text) throws IOException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("project");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ProjectDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ProjectDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理响应结果
        List<ProjectDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ProjectDoc doc = JSON.parseObject(jsonStr, ProjectDoc.class);
            docs.add(doc);
        }
        return Result.success(docs);
    }

    @GetMapping("/project/page")
    public Result<List<ProjectDoc>> pageSearchProjectByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException{
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("project");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(ProjectDoc.getFieldType(field).equals("text")){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
        }else if(ProjectDoc.getFieldType(field).equals("keyword")){
            searchSourceBuilder.query(QueryBuilders.termQuery(field, text));
        }else {
            return Result.error("该字段无法搜索");
        }
        searchSourceBuilder.from((page-1) * pageSize); // 起始位置
        searchSourceBuilder.size(pageSize); // 每页显示数量

        searchRequest.source(searchSourceBuilder);


        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


        // 处理响应结果
        List<ProjectDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ProjectDoc doc = JSON.parseObject(jsonStr, ProjectDoc.class);
            docs.add(doc);
        }
        return Result.success(docs);
    }

    @GetMapping("/project/doc")
    public Result<ProjectDoc> searchProjectById(@RequestParam int projectId) throws IOException{
        // 1.准备Request
        GetRequest request = new GetRequest("project", String.valueOf(projectId));
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            // 3.解析响应结果
            String json = response.getSourceAsString();
            ProjectDoc doc = JSON.parseObject(json, ProjectDoc.class);
            return Result.success(doc);
        }else {
            return Result.error();
        }
    }


}
