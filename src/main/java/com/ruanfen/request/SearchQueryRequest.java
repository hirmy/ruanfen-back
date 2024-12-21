package com.ruanfen.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryRequest {
    private List<SearchField> orFieldsAndTexts;  // 查询字段和文本对
    private List<SearchField> AndFieldsAndTexts;  // 查询字段和文本对
}
