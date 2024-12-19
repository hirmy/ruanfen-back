package com.ruanfen;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ArticleDoc;
import com.ruanfen.utils.ESCClientUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ESGetTest {
    private RestHighLevelClient client = ESCClientUtil.client();

    @Test
    void testGetDocumentById() throws IOException {
        // 1.准备Request
        GetRequest request = new GetRequest("article", "1");
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();

        // 转换为对象
        ArticleDoc articleDoc = JSON.parseObject(json, ArticleDoc.class);
        System.out.println(articleDoc);
    }

}
