package com.github.djbamba.gofish.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class JsonMapper {
  private static final ObjectMapper mapper = new ObjectMapper();

  private JsonMapper(){}

  static {
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
    mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public static String writeValueAsString(Object object) throws JsonProcessingException {
    return mapper.writeValueAsString(object);
  }

  public static <T> T convertValue(Object object, Class<T> clazz) {
    return mapper.convertValue(object, clazz);
  }
}
