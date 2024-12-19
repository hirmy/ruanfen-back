package com.ruanfen.constants;

public class ArticleConstant {
    //researcher_name需凭int外键获取
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "article_id": {
                    "type": "long"
                  },
                  "article_name":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "researcher_name":{
                    "type": "keyword",
                    "copy_to": "all"
                  },
                  "source":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "publish_time":{
                    "type": "date",
                    "index": "false"
                  },
                  "field_of_research":{
                    "type": "keyword",
                    "copy_to": "all"
                  },
                  "all":{
                    "type": "text",
                    "analyzer": "ik_max_word"
                  }
                }
              }
            }""";
}
