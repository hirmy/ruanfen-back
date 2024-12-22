package com.ruanfen.constants;

public class ProjectConstant {
    public static final String MAPPING_TEMPLATE = """
            {
              "mappings": {
                "properties": {
                  "projectId": {
                    "type": "long"
                  },
                  "projectName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "projectType":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "startDate":{
                    "type": "date",
                    "index": "false"
                  },
                  "endDate":{
                    "type": "date",
                    "index": "false"
                  },
                  "fieldOfResearch":{
                    "type": "text",
                    "copy_to": "all"
                  },
                  "investigatorName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "participantsName":{
                    "type": "text",
                    "analyzer": "ik_smart",
                    "copy_to": "all"
                  },
                  "all":{
                    "type": "text",
                    "analyzer": "ik_smart"
                  }
                }
              }
            }""";
}
