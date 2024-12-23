package com.ruanfen.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;

public class ESCClientUtil {
    private static RestHighLevelClient onlyClient;

    @Bean
    public static RestHighLevelClient client(){
        if(onlyClient == null){
            onlyClient = new RestHighLevelClient(RestClient.builder(
                    HttpHost.create("http://1.92.158.251:9200")
            ));
        }
        return onlyClient;
    }

}
