package com.ruanfen.Docs;

import com.ruanfen.model.Patent;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PatentDoc {
    private Long patentId;            // 专利ID
    private String patentName;        // 专利名称
    private String patentType;        // 专利类型
    private LocalDateTime applicationDate;   // 申请日期
    private String fieldOfResearch;   // 所属领域
    private String applicants;   //申请人(整体)
    private List<String> inventorsName;
    private Integer views;            // 查看次数
    private String all;               // 用于全文搜索

    public PatentDoc(Patent patent){
        this.patentId = Long.valueOf(patent.getPatentId());
        this.patentName = patent.getPatentName();
        this.patentType = patent.getPatentType().getName();
        this.applicationDate = patent.getApplicationDate();
        this.fieldOfResearch = patent.getFieldOfResearch().getName();
        this.applicants = patent.getApplicants();
        this.inventorsName = new ArrayList<>();
        this.views = patent.getViews();

//        // 将需要合并到 `all` 字段的字段值拼接
//        StringBuilder allFields = new StringBuilder();
//        allFields.append(patentName).append(",")
//                .append(patentType).append(",")
//                .append(fieldOfResearch).append(",")
//                .append(applicants).append(",");
//        this.all = allFields.toString(); // 将拼接的字段值赋值给 `all`
        this.all = " ";

    }

    public static String getFieldType(String field){
        Map<String, String> fieldTypeMap = new HashMap(){
            {
                put("patentName", "text");
                put("patentType", "keyword");
                put("fieldOfResearch", "keyword");
                put("applicants", "text");
                put("inventorsName", "text");
                put("all", "text");
            }
        };
        return fieldTypeMap.getOrDefault(field, null);

    }

    public void setInventorsName(ArrayList<String> inventorsName){
        this.inventorsName = inventorsName;
    }

    public PatentDoc(){}
}
