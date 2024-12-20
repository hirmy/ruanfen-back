package com.ruanfen.constants;

public class ArticleConstant {
    //researcher_name需凭int外键获取
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "articleId": {
                    "type": "long"
                  },
                  "articleName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "researcherName":{
                    "type": "text",
                    "copy_to": "all"
                  },
                  "source":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "publishTime":{
                    "type": "date",
                    "index": "false"
                  },
                  "fieldOfResearch":{
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
