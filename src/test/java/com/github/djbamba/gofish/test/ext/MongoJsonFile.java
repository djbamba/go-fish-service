package com.github.djbamba.gofish.test.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to provide file information containing test JSON objects to be loaded into MongoDB.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoJsonFile {

  /**
   * Name of JSON file to load.
   *
   * @return MongoDB file name.
   */
  String value();

  /**
   * Class object of elements in JSON file.
   *
   * @return class of elements in JSON file.
   */
  Class<?> classType();

  /**
   * Name of MongoDB collection storing elements.
   *
   * @return MongoDB collection name.
   */
  String collectionName();
}
