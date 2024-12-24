package com.ruanfen.Docs;

import com.ruanfen.model.Patent;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class PatentDoc {
    private Long patentId;            // 专利ID
    private String patentName;        // 专利名称
    private Date applicationDate;   // 申请日期
    private String fieldOfResearch;   // 所属领域
    private String applicants;   //申请人(整体)
    private String inventorsName;   //发明人
    private Integer views;            // 查看次数
    private String all;               // 用于全文搜索

    public PatentDoc(Patent patent){
        this.patentId = Long.valueOf(patent.getPatentId());
        this.patentName = patent.getPatentName();
        this.applicationDate = patent.getApplicationDate();
        this.fieldOfResearch = patent.getFieldOfResearch();
        this.applicants = patent.getApplicants();
        this.inventorsName = patent.getInventorsName();
        this.views = patent.getViews();

        this.all = " ";

    }

    public static String getFieldType(String field){
        Map<String, String> fieldTypeMap = new HashMap(){
            {
                put("patentName", "text");
                put("fieldOfResearch", "text");
                put("applicants", "text");
                put("inventorsName", "text");
                put("all", "text");
            }
        };
        return fieldTypeMap.getOrDefault(field, null);

    }



    public PatentDoc(){}
}
