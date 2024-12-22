// filepath: /c:/Users/lenovo/Desktop/ruanfen-back/src/main/java/com/ruanfen/constants/MessageConstant.java
package com.ruanfen.constants;

public class MessageConstant {
    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"message_id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"sender_id\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"receiver_id\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"category\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"title\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"content\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"created_time\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"updated_time\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"if_confirmed\": {\n" +
            "        \"type\": \"boolean\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}