package com.ruanfen.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruanfen.Docs.ArticleDoc;
import com.ruanfen.Docs.ResearcherDoc;
import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;
import com.ruanfen.service.ResearcherService;
import com.ruanfen.service.SearchService;
import com.ruanfen.utils.ESCClientUtil;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ResearcherService researcherService;

    private RestHighLevelClient client = ESCClientUtil.client();

    @Override
    public void addArticleDoc(Article article) throws IOException {
        IndexRequest request = new IndexRequest("article");

        ArticleDoc articleDoc = new ArticleDoc(article);

        // 2.2.创建新增文档的Request对象
        request.id(String.valueOf(articleDoc.getArticleId()))
                .source(JSON.toJSONString(articleDoc), XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);
        return;
    }

    @Override
    public void deleteArticleDoc(int articleId) throws IOException {
        DeleteRequest request = new DeleteRequest("article", String.valueOf(articleId));

        // 执行删除操作
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Override
    public void updateArticleDoc(int articleId, Article article) throws IOException {
        // 查询已有文档的请求对象
        GetRequest getRequest = new GetRequest("article", String.valueOf(articleId));

        // 检查文档是否存在
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (!exists) {
            throw new IllegalArgumentException("Document with ID " + articleId + " does not exist.");
        }

        // 创建更新文档的请求对象
        UpdateRequest request = new UpdateRequest("article", String.valueOf(articleId));

        // 创建更新内容
        ArticleDoc articleDoc = new ArticleDoc(article);

        // 设置更新内容
        request.doc(JSON.toJSONString(articleDoc), XContentType.JSON);

        // 执行更新操作
        client.update(request, RequestOptions.DEFAULT);
    }

    @Override
    public void addResearcherDoc(Researcher researcher) throws IOException {
        // 创建新增文档的请求对象
        IndexRequest request = new IndexRequest("researcher");

        // 构建 ResearcherDoc 对象
        ResearcherDoc researcherDoc = new ResearcherDoc(researcher);

        // 设置文档的 ID 和内容
        request.id(String.valueOf(researcherDoc.getResearcherId()))
                .source(JSON.toJSONString(researcherDoc), XContentType.JSON);

        // 执行新增操作
        client.index(request, RequestOptions.DEFAULT);
    }

    @Override
    public void removeResearcherDoc(int researcherId) throws IOException {
        // 创建删除文档的请求对象
        DeleteRequest request = new DeleteRequest("researcher", String.valueOf(researcherId));

        // 执行删除操作
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Override
    public void updateResearcherDoc(int researcherId, Researcher researcher) throws IOException {
        // 查询已有文档的请求对象
        GetRequest getRequest = new GetRequest("researcher", String.valueOf(researcherId));

        // 检查文档是否存在
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (!exists) {
            throw new IllegalArgumentException("Researcher with ID " + researcherId + " does not exist.");
        }

        // 创建更新文档的请求对象
        UpdateRequest request = new UpdateRequest("researcher", String.valueOf(researcherId));

        // 构建更新内容
        ResearcherDoc researcherDoc = new ResearcherDoc(researcher);

        // 设置更新内容
        request.doc(JSON.toJSONString(researcherDoc), XContentType.JSON);

        // 执行更新操作
        client.update(request, RequestOptions.DEFAULT);
    }
}
