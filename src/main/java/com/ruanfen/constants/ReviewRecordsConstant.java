package com.ruanfen.constants;

public class ReviewRecordsConstant {
    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"record_id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"creater_id\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"reviewer_id\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"review_date\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"request_type\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"action\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"coment\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}