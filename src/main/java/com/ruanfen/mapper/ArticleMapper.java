package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> searchArticles(@Param("articleName") String articleName,
                                 @Param("keywords") String keywords,
                                 @Param("fieldOfResearch") String fieldOfResearch,
                                 @Param("publishTimeFrom") String publishTimeFrom,
                                 @Param("publishTimeTo") String publishTimeTo);
}