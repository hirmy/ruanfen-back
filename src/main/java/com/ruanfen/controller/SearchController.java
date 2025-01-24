package com.ruanfen.controller;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.*;
import com.ruanfen.model.Result;
import com.ruanfen.request.SearchField;
import com.ruanfen.request.SearchQueryRequest;
import com.ruanfen.result.ArticleDocResult;
import com.ruanfen.result.PatentDocResult;
import com.ruanfen.result.ResearcherDocResult;
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
import java.util.function.BiFunction;

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


    @PostMapping("/article/cond")
    public Result<ArticleDocResult> searchArticleByCondFields(@RequestBody SearchQueryRequest searchQueryRequest) throws IOException{
        return condSearch("article", searchQueryRequest, "article", ArticleDoc.class);
    }

    @GetMapping("/article/page")
    public Result<ArticleDocResult> pageSearchArticleByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException {
        // 缓存键生成
        String cacheKey =  "article:search:" + field + ":" + text + ":page:" + page + ":size:" + pageSize;

        // 先尝试从缓存中获取数据
        ArticleDocResult cachedDocs = (ArticleDocResult) redisTemplate.opsForValue().get(cacheKey);
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

        long totalHits = searchResponse.getHits().getTotalHits().value;

        // 处理响应结果
        List<ArticleDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }
        ArticleDocResult articleDocResult = new ArticleDocResult(docs, Integer.parseInt(String.valueOf(totalHits)));
        // 将查询结果存入缓存，并设置缓存过期时间（例如1小时）
        redisTemplate.opsForValue().set(cacheKey, articleDocResult, Duration.ofMinutes(30));

        return Result.success(articleDocResult);

    }

    @GetMapping("/article/page/order")
    public Result<ArticleDocResult> pageSearchArticleByFieldOrder(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize, @RequestParam String orderField, @RequestParam int desc) throws IOException {
        // 缓存键生成
        String cacheKey =  "article:search:order:" + field + ":" + text + ":page:" + page + ":size:" + pageSize + ":orderField:" + orderField + ":desc:" + desc;

        // 先尝试从缓存中获取数据
        ArticleDocResult cachedDocs = (ArticleDocResult) redisTemplate.opsForValue().get(cacheKey);

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

        //排序
        SortOrder sortOrder = desc == 1 ? SortOrder.DESC : SortOrder.ASC;
        searchSourceBuilder.sort(orderField, sortOrder); // 设置排序

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        long totalHits = searchResponse.getHits().getTotalHits().value;
        // 处理响应结果
        List<ArticleDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(jsonStr, ArticleDoc.class);
            docs.add(articleDoc);
        }
        // 将查询结果存入缓存，并设置缓存过期时间（例如1小时）
        ArticleDocResult articleDocResult = new ArticleDocResult(docs, Integer.parseInt(String.valueOf(totalHits)));
        redisTemplate.opsForValue().set(cacheKey, articleDocResult, Duration.ofMinutes(30));

        return Result.success(articleDocResult);

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
    public Result<ResearcherDocResult> searchResearcherByCondFields(@RequestBody SearchQueryRequest searchQueryRequest) throws IOException{
        return condSearch("researcher", searchQueryRequest, "researcher", ResearcherDoc.class);
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

    @GetMapping("/researcher/page/order")
    public Result<List<ResearcherDoc>> pageSearchResearcherByFieldOrder(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize, @RequestParam String orderField, @RequestParam int desc) throws IOException{
        // 缓存键生成
        String cacheKey = "researcher:search:order:" + field + ":" + text + ":page:" + page + ":size:" + pageSize + ":orderField:" + orderField + ":desc:" + desc;;

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

        //排序
        SortOrder sortOrder = desc == 1 ? SortOrder.DESC : SortOrder.ASC;
        searchSourceBuilder.sort(orderField, sortOrder); // 设置排序

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


    @PostMapping("/patent/cond")
    public Result<PatentDocResult> searchPatentByCondFields(@RequestBody SearchQueryRequest searchQueryRequest) throws IOException{
       return condSearch("patent", searchQueryRequest, "patent", PatentDoc.class);
    }

    @GetMapping("/patent/page")
    public Result<PatentDocResult> pageSearchPatentByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException{
        // 缓存键生成
        String cacheKey = "patent:search:" + field + ":" + text + ":page:" + page + ":size:" + pageSize;

        // 先尝试从缓存中获取数据
        PatentDocResult cachedDocs = (PatentDocResult) redisTemplate.opsForValue().get(cacheKey);

        // 如果缓存中有数据，直接返回
        if (cachedDocs != null) {
            return Result.success(cachedDocs);
        }

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

        long totalHits = searchResponse.getHits().getTotalHits().value;

        // 处理响应结果
        List<PatentDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            PatentDoc doc = JSON.parseObject(jsonStr, PatentDoc.class);
            docs.add(doc);
        }
        PatentDocResult patentDocResult = new PatentDocResult(docs, Integer.parseInt(String.valueOf(totalHits)));
        redisTemplate.opsForValue().set(cacheKey, patentDocResult, Duration.ofMinutes(30));

        return Result.success(patentDocResult);
    }


    @GetMapping("/patent/page/order")
    public Result<PatentDocResult> pageSearchPatentByFieldOrder(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize, @RequestParam String orderField, @RequestParam int desc) throws IOException{
        // 缓存键生成
        String cacheKey = "patent:search:order:" + field + ":" + text + ":page:" + page + ":size:" + pageSize + ":orderField:" + orderField + ":desc:" + desc;;

        // 先尝试从缓存中获取数据
        PatentDocResult cachedDocs = (PatentDocResult) redisTemplate.opsForValue().get(cacheKey);

        // 如果缓存中有数据，直接返回
        if (cachedDocs != null) {
            return Result.success(cachedDocs);
        }

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

        //排序
        SortOrder sortOrder = desc == 1 ? SortOrder.DESC : SortOrder.ASC;
        searchSourceBuilder.sort(orderField, sortOrder); // 设置排序

        searchRequest.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        long totalHits = searchResponse.getHits().getTotalHits().value;
        // 处理响应结果
        List<PatentDoc> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            PatentDoc doc = JSON.parseObject(jsonStr, PatentDoc.class);
            docs.add(doc);
        }
        PatentDocResult patentDocResult = new PatentDocResult(docs, Integer.parseInt(String.valueOf(totalHits)));
        redisTemplate.opsForValue().set(cacheKey, patentDocResult, Duration.ofMinutes(30));

        return Result.success(patentDocResult);
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

    private <T,E> Result<T> condSearch(String cacheKeyPrefix, SearchQueryRequest searchQueryRequest,
                                        String indexName, Class<E> docClass) throws IOException {
        // 1. 构建缓存 Key
        String cacheKey = cacheKeyPrefix + ":" + searchQueryRequest.generateCacheKey();

        // 2. 尝试从缓存获取数据
        T cachedDocs = (T) redisTemplate.opsForValue().get(cacheKey);
        if (cachedDocs != null) {
            return Result.success(cachedDocs); // 缓存命中
        }

        // 3. 创建 SearchRequest，指定索引
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);

        // 4. 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = buildQuery(searchQueryRequest, indexName);

        searchSourceBuilder.query(boolQuery);
        // 设置分页
        int page = searchQueryRequest.getPage();
        int pageSize = searchQueryRequest.getPageSize();
        searchSourceBuilder.from((page - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        // 设置排序
        if (searchQueryRequest.getOrderField() != null && !searchQueryRequest.getOrderField().isEmpty()) {
            String orderField = searchQueryRequest.getOrderField();
            SortOrder sortOrder = searchQueryRequest.getDesc() == 1 ? SortOrder.DESC : SortOrder.ASC;
            searchSourceBuilder.sort(orderField, sortOrder);
        }

        searchRequest.source(searchSourceBuilder);

        // 5. 执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 6. 解析响应数据
        List<E> docs = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String jsonStr = hit.getSourceAsString();
            E doc = JSON.parseObject(jsonStr, docClass);
            docs.add(doc);
        }

        // 构建返回结果
        if (docClass == ArticleDoc.class) {
            long totalHits = searchResponse.getHits().getTotalHits().value;
            ArticleDocResult result = new ArticleDocResult((List<ArticleDoc>) docs, (int) totalHits);
            redisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(30));
            return (Result<T>) Result.success(result);
        }else if(docClass == PatentDoc.class) {
            long totalHits = searchResponse.getHits().getTotalHits().value;
            PatentDocResult result = new PatentDocResult((List<PatentDoc>) docs, (int) totalHits);
            redisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(30));
            return (Result<T>) Result.success(result);
        }else if(docClass == ResearcherDoc.class){
            long totalHits = searchResponse.getHits().getTotalHits().value;
            ResearcherDocResult result = new ResearcherDocResult((List<ResearcherDoc>) docs, (int) totalHits);
            redisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(30));
            return (Result<T>) Result.success(result);
        }else {
            return null;
        }
    }

    private BoolQueryBuilder buildQuery(SearchQueryRequest searchQueryRequest, String indexName) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        Doc document;
        switch (indexName){
            case "article":
                document = new ArticleDoc();
                break;
            case "researcher":
                document = new ResearcherDoc();
                break;
            case "patent":
                document = new PatentDoc();
                break;
            default:
                document = null;
                break;
        }
        // OR 查询部分
        BoolQueryBuilder orQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getOrFieldsAndTexts()) {
            String fieldName = searchField.getField();

            String fieldType = getFieldTypeForDoc(document, fieldName);
            if ("text".equals(fieldType)) {
                orQuery.should(QueryBuilders.matchQuery(searchField.getField(), searchField.getText()));
            } else if ("keyword".equals(fieldType)) {
                orQuery.should(QueryBuilders.termQuery(searchField.getField(), searchField.getText()));
            } else {
                throw new IllegalArgumentException("该字段无法搜索");
            }
        }
        boolQuery.must(orQuery);

        // AND 查询部分
        BoolQueryBuilder andQuery = QueryBuilders.boolQuery();
        for (SearchField searchField : searchQueryRequest.getAndFieldsAndTexts()) {
            String fieldName = searchField.getField();
            String fieldType = getFieldTypeForDoc(document, fieldName);

            if ("text".equals(fieldType)) {
                andQuery.must(QueryBuilders.matchQuery(searchField.getField(), searchField.getText()));
            } else if ("keyword".equals(fieldType)) {
                andQuery.must(QueryBuilders.termQuery(searchField.getField(), searchField.getText()));
            } else {
                throw new IllegalArgumentException("该字段无法搜索");
            }
        }
        boolQuery.must(andQuery);

        return boolQuery;
    }

    private String getFieldTypeForDoc(Doc document, String fieldName) {
        if (document instanceof ArticleDoc) {
            return ArticleDoc.getFieldType(fieldName);
        } else if (document instanceof ResearcherDoc) {
            return ResearcherDoc.getFieldType(fieldName);
        } else if (document instanceof PatentDoc) {
            return PatentDoc.getFieldType(fieldName);
        }
        return null;
    }




}
