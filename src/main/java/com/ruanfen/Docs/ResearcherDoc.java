package com.ruanfen.Docs;

import com.ruanfen.model.Researcher;
import lombok.Data;

@Data
public class ResearcherDoc {
    private Long researcherId;  // researcher_id, 类型为 long
    private String name;        // name，使用 ik_smart 分析器，并且复制到 all 字段
    private String institution; // institution，使用 ik_smart 分析器，并且复制到 all 字段
    private String articleIds;  // article_ids，不做索引
    private String patentIds;   // patent_ids，不做索引
    private String projectIds;  // project_ids，不做索引
    private String fieldOfResearch;  // field_of_research，类型为 keyword，复制到 all 字段
    private String all;          // all，使用 ik_max_word 分析器

    public ResearcherDoc(Researcher entity){
        this.researcherId = Long.valueOf(entity.getResearcherId());
        this.name = entity.getName();
        this.institution = entity.getInstitution();
        this.articleIds = entity.getArticleIds();
        this.patentIds = entity.getPatentIds();
        this.projectIds = entity.getProjectIds();
        this.fieldOfResearch = entity.getFieldOfResearch().getName();

        StringBuilder allFields = new StringBuilder();
        allFields.append(name).append(" ")
                .append(institution)
                .append(" ")
                .append(fieldOfResearch);
        this.all = allFields.toString(); // 将拼接的字段值赋值给 `all`
    }

    public ResearcherDoc(){}

}
