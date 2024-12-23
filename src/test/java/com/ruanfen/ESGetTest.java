//package com.ruanfen;
//
//import com.alibaba.fastjson.JSON;
//import com.ruanfen.Docs.ArticleDoc;
//import com.ruanfen.utils.ESCClientUtil;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//
//@SpringBootTest
//public class ESGetTest {
//    private RestHighLevelClient client = ESCClientUtil.client();
//    @Autowired
//    private StringRedisTemplate template;
//
//    @Test
//    @Transactional
//    void testGetDocumentById() throws IOException {
//
//        template.setEnableTransactionSupport(true);
//
//        template.multi();
//        template.opsForValue().set("a", "123");
//        template.exec();
//
//    }
//
//}
