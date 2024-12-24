package com.ruanfen.result;

import com.ruanfen.Docs.ArticleDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDocResult {
    private List<ArticleDoc> articleDocs;
    private int count;

}
