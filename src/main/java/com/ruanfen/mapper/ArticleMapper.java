package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    @Select({
            "<script>",
            "SELECT * FROM article",
            "WHERE 1 = 1",
            "<if test='articleName != null'> AND article_name LIKE CONCAT('%', #{articleName}, '%') </if>",
            "<if test='keywords != null'> AND keywords LIKE CONCAT('%', #{keywords}, '%') </if>",
            "<if test='fieldOfResearch != null'> AND field_of_research LIKE CONCAT('%', #{fieldOfResearch}, '%') </if>",
            "<if test='publishTimeFrom != null'> AND publish_time &gt;= #{publishTimeFrom} </if>",
            "<if test='publishTimeTo != null'> AND publish_time &lt;= #{publishTimeTo} </if>",
            "</script>"
    })
    List<Article> searchArticles(@Param("articleName") String articleName,
                                 @Param("keywords") String keywords,
                                 @Param("fieldOfResearch") String fieldOfResearch,
                                 @Param("publishTimeFrom") String publishTimeFrom,
                                 @Param("publishTimeTo") String publishTimeTo);
}
