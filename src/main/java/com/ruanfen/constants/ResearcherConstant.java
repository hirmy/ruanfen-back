package com.ruanfen.constants;

public class ResearcherConstant {
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "researcher_id": {
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
                  "article_ids":{
                    "type": "text",
                    "index": false
                  },
                  "patent_ids":{
                    "type": "text",
                    "index": false
                  },
                  "project_ids":{
                    "type": "text",
                    "index": false
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
