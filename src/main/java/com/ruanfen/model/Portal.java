package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("portal")
public class Portal {
    @TableId(value = "portal_id", type = IdType.AUTO)  // 主键，自增
    private Integer portalId;

    @TableField("is_claimed")  // 映射字段名
    private Boolean isClaimed;  // 是否已被认领

    @TableField("belong_user_id")  // 外键，记录门户主人的ID
    private Integer belongUserId;

    @TableField("created_time")  // 创建时间
    private LocalDateTime createdTime;

    @TableField("claimed_time")  // 认领时间
    private LocalDateTime claimedTime;

    @TableField("administrator_id")  // 外键，处理门户认领审核的管理员ID
    private Integer administratorId;

    @TableField("Science_id")  // 外键，连接对应的科研人员信息
    private Integer scienceId;
}
