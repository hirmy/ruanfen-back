package com.ruanfen.controller;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ArticleDoc;
import com.ruanfen.model.Result;
import com.ruanfen.utils.ESCClientUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private RestHighLevelClient client = ESCClientUtil.client();

    @GetMapping("/article/allArticle")
    public Result<List<ArticleDoc>> searchArticleAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("article");

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

        return Result.success(docs);
    }

    @GetMapping("/article")
    public Result<List<ArticleDoc>> searchArticleByField(@RequestParam String field, @RequestParam String text) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("article");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));

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

        return Result.success(docs);

    }

    @GetMapping("/article/page")
    public Result<List<ArticleDoc>> pageSearchArticleByField(@RequestParam String field, @RequestParam String text, @RequestParam int page, @RequestParam int pageSize) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("article");

        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field, text));
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

        return Result.success(docs);

    }

    @GetMapping("/article/doc")
    public Result<ArticleDoc> searchArticleById(@RequestParam int articleId) throws IOException {
        // 1.准备Request
        GetRequest request = new GetRequest("article", String.valueOf(articleId));
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            // 3.解析响应结果
            String json = response.getSourceAsString();
            ArticleDoc articleDoc = JSON.parseObject(json, ArticleDoc.class);
            return Result.success(articleDoc);
        }else {
            return Result.error();
        }



    }


}
