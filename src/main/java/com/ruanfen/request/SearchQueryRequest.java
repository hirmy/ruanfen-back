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

    // 生成 Redis 缓存键的方法
    public String generateCacheKey() {
        StringBuilder cacheKey = new StringBuilder("search:");

        this.getOrFieldsAndTexts().forEach(field -> {
            cacheKey.append(field.getField()).append(":").append(field.getText()).append(":OR;");
        });
        this.getAndFieldsAndTexts().forEach(field -> {
            cacheKey.append(field.getField()).append(":").append(field.getText()).append(":AND;");
        });

        return cacheKey.toString();
    }
}
