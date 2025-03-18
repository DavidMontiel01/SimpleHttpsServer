package com.SimpleHttpsServer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    private static final ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static JsonNode parse(String source) throws IOException {
        return myObjectMapper.readTree(source);
    }

    public static <T> T  fromJson(JsonNode jsonNode, Class<T> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(jsonNode, clazz);
    }

    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException{
        ObjectWriter myWriter = myObjectMapper.writer();
        if(pretty){
            myWriter = myWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return myWriter.writeValueAsString(o);
    }
}
