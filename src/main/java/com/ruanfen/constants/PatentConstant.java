package com.ruanfen.constants;

public class PatentConstant {
    //applicants为字符串："wang,li,chen"
    //inventors_name为字符串数组["li", "kao"]
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "patentId": {
                    "type": "long"
                  },
                  "patentName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "applicationDate":{
                    "type": "date",
                    "index": "false"
                  },
                  "fieldOfResearch":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "applicants":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "inventorsName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                   "views":{
                    "type": "integer"
                  },
                  "all":{
                    "type": "text",
                    "analyzer": "ik_max_word"
                  }
                }
              }
            }""";
}
