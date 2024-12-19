package com.ruanfen.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;

public class ESCClientUtil {
    private static RestHighLevelClient onlyClient;

    @Bean
    public RestHighLevelClient client(){
        if(onlyClient == null){
            onlyClient = new RestHighLevelClient(RestClient.builder(
                    HttpHost.create("http://127.0.0.1:9200")
            ));
        }
        return onlyClient;
    }

}
