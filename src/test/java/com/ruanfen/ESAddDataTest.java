package com.ruanfen;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ArticleDoc;
import com.ruanfen.Docs.ResearcherDoc;
import com.ruanfen.Docs.UserDoc;
import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.User;
import com.ruanfen.service.ArticleService;
import com.ruanfen.service.ResearcherService;
import com.ruanfen.service.UserService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
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
public class ESAddDataTest {

    private RestHighLevelClient client;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;


    @Autowired
    private ResearcherService researcherService;

    @Test
    public void addData2User() throws IOException {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
        ));

        List<User> users = userService.list();

        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加多个新增的Request
        for (User user : users) {
            // 2.1.转换为文档类型HotelDoc
            UserDoc userDoc = new UserDoc(user);
            // 2.2.创建新增文档的Request对象
            request.add(new IndexRequest("user")
                    .id(String.valueOf(userDoc.getUserId()))
                    .source(JSON.toJSONString(userDoc), XContentType.JSON));
        }
        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
        this.client.close();

    }

    @Test
    public void addData2Article() throws IOException{
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
        ));

        List<Article> articles = articleService.list();

        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加多个新增的Request
        for (Article article : articles) {
            // 2.1.转换为文档类型
            ArticleDoc articleDoc = new ArticleDoc(article);

            int researcherId = article.getResearcherId();
            String researcherName = researcherService.getById(researcherId).getName();
            articleDoc.setResearcherName(researcherName);

            // 2.2.创建新增文档的Request对象
            request.add(new IndexRequest("article")
                    .id(String.valueOf(articleDoc.getArticleId()))
                    .source(JSON.toJSONString(articleDoc), XContentType.JSON));
        }
        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
        this.client.close();

    }

    @Test
    public void addData2Researcher() throws IOException{
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
        ));

        List<Researcher> researchers = researcherService.list();

        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加多个新增的Request
        for (Researcher researcher : researchers) {
            // 2.1.转换为文档类型
            ResearcherDoc researcherDoc = new ResearcherDoc(researcher);

            // 2.2.创建新增文档的Request对象
            request.add(new IndexRequest("researcher")
                    .id(String.valueOf(researcherDoc.getResearcherId()))
                    .source(JSON.toJSONString(researcherDoc), XContentType.JSON));
        }
        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
        this.client.close();

    }

}
