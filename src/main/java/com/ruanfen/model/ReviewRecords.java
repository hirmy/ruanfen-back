package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("reviewrecords")
public class ReviewRecords {
    @TableId(value = "record_id", type = IdType.AUTO) // 主键，自增
    private Integer recordId;

    @TableField("creater_id") // 发起者ID
    private Integer createrId;

    @TableField("reviewer_id") // 审核者ID
    private Integer reviewerId;

    @TableField("review_date") // 审核时间
    private LocalDateTime reviewDate;

    @TableField("request_type") // 请求类型
    private String requestType;

    @TableField("action") // 操作结果
    private String action;

    @TableField("coment") // 审核评论
    private String coment;
}