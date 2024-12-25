package com.ruanfen;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.UserDoc;
import com.ruanfen.constants.*;
import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;



@SpringBootTest
public class ESAddIndexTest {
    private RestHighLevelClient client;


    @Test
    public void createIndex() throws IOException {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://1.92.158.251:9200")
        ));
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("article");
        // 2.准备请求的参数：DSL语句
        request.source(ArticleConstant.MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
        this.client.close();

    }


}
