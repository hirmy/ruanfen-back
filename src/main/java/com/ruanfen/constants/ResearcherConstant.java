package com.ruanfen.constants;

public class ResearcherConstant {
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "researcherId": {
                    "type": "long"
                  },
                  "name":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "institution":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "articleIds":{
                    "type": "text",
                    "index": false
                  },
                  "patentIds":{
                    "type": "text",
                    "index": false
                  },
                  "projectIds":{
                    "type": "text",
                    "index": false
                  },
                  "fieldOfResearch":{
                    "type": "text",
                    "analyzer": "ik_smart",
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
