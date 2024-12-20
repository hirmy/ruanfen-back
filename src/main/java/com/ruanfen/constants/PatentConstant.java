package com.ruanfen.constants;

public class PatentConstant {
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
