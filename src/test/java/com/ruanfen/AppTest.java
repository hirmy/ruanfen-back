//package com.ruanfen;
//
//import com.alibaba.fastjson.JSON;
//import com.ruanfen.Docs.ResearcherDoc;
//import com.ruanfen.model.Article;
//import com.ruanfen.model.Result;
//import com.ruanfen.service.ArticleService;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class AppTest {
//    @Autowired
//    private ArticleService articleService;
//    private RestHighLevelClient client;
//
//
//    @Test
//    public void testArticle() throws IOException {
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://127.0.0.1:9200")
//        ));
//
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("researcher");
//
//        //2.创建 SearchSourceBuilder条件构造。
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchQuery("all", "Alice"));
//        searchSourceBuilder.from((2-1) * 1); // 起始位置
//        searchSourceBuilder.size(1); // 每页显示数量
//
//        searchRequest.source(searchSourceBuilder);
//
//        // 执行搜索
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//
//
//
//        // 处理响应结果
//        List<ResearcherDoc> docs = new ArrayList<>();
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            String jsonStr = hit.getSourceAsString();
//            ResearcherDoc researcherDoc = JSON.parseObject(jsonStr, ResearcherDoc.class);
//            docs.add(researcherDoc);
//        }
//        this.client.close();
//        System.out.println(docs);
//        return;
//    }
//
//}
