package com.github.djbamba.gofish.test.ext;

import org.springframework.data.mongodb.core.MongoTemplate;

public interface MongoTemplateProvider {

  MongoTemplate getMongoTemplate();
}
