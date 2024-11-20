package com.ruanfen.pojo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private int id;
    private String title;//文章标题
    private String content;//文章内容(10000)
    private String coverImg;//封面图像
    private String state;//发布状态 如：已发布，已下架，未通过审核
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
