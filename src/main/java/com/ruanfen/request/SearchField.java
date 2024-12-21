package com.ruanfen.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchField {
    private String field;  // 字段名
    private String text;   // 查询文本
}
