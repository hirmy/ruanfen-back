package com.ruanfen.Docs;

import com.ruanfen.model.Project;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProjectDoc {
    private Long projectId; // 项目编号
    private String projectName; // 项目名称
    private String projectType; // 项目类型
    private LocalDateTime startDate; // 项目开始日期
    private LocalDateTime endDate; // 项目结束日期
    private String fieldOfResearch; // 研究领域
    private String investigatorName; // 主要负责人姓名
    private List<String> participantsName; // 参与者姓名
    private String all; // 汇总字段，用于全文搜索

    public ProjectDoc(Project project){
        this.projectId = Long.valueOf(project.getProjectId());
        this.projectName = project.getProjectName();
        this.projectType = project.getProjectType();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.fieldOfResearch = project.getFieldOfResearch().getName();

        this.all = " ";
    }

    public void setInvestigatorName(String name){
        this.investigatorName = name;
    }

    public void setParticipantsName(ArrayList<String> names){
        this.participantsName = names;
    }

    public static String getFieldType(String field) {
        Map<String, String> fieldTypeMap = new HashMap<>() {{
            put("projectName", "text");
            put("projectType", "text");
            put("fieldOfResearch", "keyword");
            put("investigatorName", "text");
            put("participantsName", "text");
            put("all", "text");
        }};

        return fieldTypeMap.getOrDefault(field, null);
    }

    public ProjectDoc(){}

}
