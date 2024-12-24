//package com.ruanfen;
//
//import com.alibaba.fastjson.JSON;
//import com.ruanfen.Docs.*;
//import com.ruanfen.model.*;
//import com.ruanfen.service.*;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class ESAddDataTest {
//
//    private RestHighLevelClient client;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ArticleService articleService;
//
//    @Autowired
//    private ResearcherService researcherService;
//
//    @Autowired
//    private PatentService patentService;
//
//    @Autowired
//    ProjectService projectService;
//
//    @Test
//    public void addData2User() throws IOException {
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://127.0.0.1:9200")
//        ));
//
//        List<User> users = userService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (User user : users) {
//            // 2.1.转换为文档类型HotelDoc
//            UserDoc userDoc = new UserDoc(user);
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("user")
//                    .id(String.valueOf(userDoc.getUserId()))
//                    .source(JSON.toJSONString(userDoc), XContentType.JSON));
//        }
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
//        this.client.close();
//    }
//
//
//    @Test
//    public void addData2Article() throws IOException{
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://1.92.158.251:9200")
//        ));
//
//        List<Article> articles = articleService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (Article article : articles) {
//            // 2.1.转换为文档类型
//            ArticleDoc articleDoc = new ArticleDoc(article);
//
//
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("article")
//                    .id(String.valueOf(articleDoc.getArticleId()))
//                    .source(JSON.toJSONString(articleDoc), XContentType.JSON));
//        }
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
//        this.client.close();
//
//    }
//
//    @Test
//    public void addData2Researcher() throws IOException{
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://1.92.158.251:9200")
//        ));
//
//        List<Researcher> researchers = researcherService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (Researcher researcher : researchers) {
//            // 2.1.转换为文档类型
//            ResearcherDoc researcherDoc = new ResearcherDoc(researcher);
//
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("researcher")
//                    .id(String.valueOf(researcherDoc.getResearcherId()))
//                    .source(JSON.toJSONString(researcherDoc), XContentType.JSON));
//        }
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
//        this.client.close();
//
//    }
//
//    @Test
//    public void addData2Patent() throws IOException{
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://1.92.158.251:9200")
//        ));
//
//        List<Patent> patents = patentService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (Patent patent : patents) {
//            // 2.1.转换为文档类型
//            PatentDoc patentDoc = new PatentDoc(patent);
//
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("patent")
//                    .id(String.valueOf(patentDoc.getPatentId()))
//                    .source(JSON.toJSONString(patentDoc), XContentType.JSON));
//        }
//
//
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
//        this.client.close();
//    }
//
//    @Test
//    public void addData2Project() throws IOException{
//        this.client = new RestHighLevelClient(RestClient.builder(
//                HttpHost.create("http://127.0.0.1:9200")
//        ));
//
//        List<Project> projects = projectService.list();
//
//        // 1.创建Request
//        BulkRequest request = new BulkRequest();
//        // 2.准备参数，添加多个新增的Request
//        for (Project project : projects) {
//            // 2.1.转换为文档类型
//            ArrayList<String> participantsName = new ArrayList<>();
//            ProjectDoc projectDoc = new ProjectDoc(project);
//            int investigator_id = (project.getInvestigatorId());
//            String participants_ids = project.getParticipantsId();
//
//            //获取主要负责人姓名
//            String investigator_name = researcherService.getNameById(investigator_id);
//
//            //获取参与者名字
//            String[] ids = participants_ids.split(",");
//            ArrayList<String> ptNames = new ArrayList<>();
//            String ptName;
//            for (String id : ids) {
//                id = id.trim();
//                ptName = researcherService.getNameById(Integer.parseInt(id));
//                if(ptName != null){
//                    ptNames.add(ptName);
//                }
//            }
//
//            projectDoc.setInvestigatorName(investigator_name);
//            projectDoc.setParticipantsName(ptNames);
//
//            // 2.2.创建新增文档的Request对象
//            request.add(new IndexRequest("project")
//                    .id(String.valueOf(projectDoc.getProjectId()))
//                    .source(JSON.toJSONString(projectDoc), XContentType.JSON));
//        }
//
//
//        // 3.发送请求
//        client.bulk(request, RequestOptions.DEFAULT);
//        this.client.close();
//    }
//
//}
