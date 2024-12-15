package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import com.ruanfen.enums.FieldOfResearch;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("project")
public class project {
    @TableId(value = "project_id", type = IdType.AUTO) // 主键自增
    private Integer projectId;

    private String projectName; // 项目名称

    private String projectType; // 项目类型

    private LocalDateTime startDate; // 项目开始日期

    private LocalDateTime endDate; // 项目结束日期

    private String fundingSources; // 资金来源

    @EnumValue
    private FieldOfResearch fieldOfResearch; // 研究领域

    private Integer investigatorId; // 主要负责人ID fk

    private String participantsId; // 参与者ID串

    private String projectDescription; // 项目描述

    private String projectStatus; // 项目状态
}
