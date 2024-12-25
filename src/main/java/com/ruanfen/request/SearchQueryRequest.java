package com.ruanfen.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchQueryRequest {
    private List<SearchField> orFieldsAndTexts;  // 查询字段和文本对
    private List<SearchField> AndFieldsAndTexts;  // 查询字段和文本对
    private String orderField;
    private int desc;
    private int page;
    private int pageSize;

    public SearchQueryRequest(List<SearchField> orFieldsAndTexts,List<SearchField> andFieldsAndTexts, String orderField, int desc, int page, int pageSize){
        this.orderField = orderField;
        this.AndFieldsAndTexts = andFieldsAndTexts;
        if(orderField == null){
            this.orderField = null;
            this.desc = -1;
        }else {
            this.orderField = orderField;
            this.desc = desc;
        }
        this.page = page;
        this.pageSize = pageSize;
    }

    // 生成 Redis 缓存键的方法
    public String generateCacheKey() {
        StringBuilder cacheKey = new StringBuilder("search:");

        this.getOrFieldsAndTexts().forEach(field -> {
            cacheKey.append(field.getField()).append(":").append(field.getText()).append(":OR;");
        });
        this.getAndFieldsAndTexts().forEach(field -> {
            cacheKey.append(field.getField()).append(":").append(field.getText()).append(":AND;");
        });
        if(this.getOrderField()!= null){
            cacheKey.append(this.getOrderField()).append(":").append(String.valueOf(this.desc));
        }

        return cacheKey.toString();
    }
}
