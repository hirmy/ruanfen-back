package com.ruanfen.service;


import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;

import java.io.IOException;

public interface SearchService {
    void addArticleDoc(Article article) throws IOException;
    void deleteArticleDoc(int articleId) throws IOException;
    void updateArticleDoc(int articleId, Article article) throws IOException;

    void addResearcherDoc(Researcher researcher) throws IOException;
    void removeResearcherDoc(int researcherId) throws IOException;
    void updateResearcherDoc(int researcherId, Researcher researcher) throws IOException;


}
