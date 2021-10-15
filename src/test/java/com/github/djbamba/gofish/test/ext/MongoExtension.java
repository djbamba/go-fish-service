package com.github.djbamba.gofish.test.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Extension to manage seeding/clearing the mongo collections used in integration tests. To use this
 * extension the test class must implement the MongoTemplateProvider interface and annotate test
 * methods with {@literal @}MongoJsonFile
 */
@Slf4j
public class MongoExtension implements BeforeEachCallback, AfterEachCallback {

  private final Function<String, Path> TEST_DATA = fileName -> Paths.get("src", "test",
      "resources", "data", "json").resolve(fileName);
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    context.getTestMethod().ifPresent(testMethod -> {
      Optional<MongoJsonFile> mongoJsonFile = Optional.ofNullable(
          testMethod.getAnnotation(MongoJsonFile.class));
      mongoJsonFile.ifPresent(testFile -> getMongoTemplate(context).ifPresent(template -> {
        try {
          List<?> objects = mapper.readValue(TEST_DATA.apply(testFile.value()).toFile(),
              mapper.getTypeFactory()
                  .constructCollectionType(List.class, testFile.classType()));
          objects.forEach(template::save);
        } catch (Exception e) {
          log.error("Error mapping JSON in beforeEach", e);
        }
      }));
    });
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    context.getTestMethod().ifPresent(testMethod -> {
      Optional<MongoJsonFile> mongoJsonFile = Optional.ofNullable(
          testMethod.getAnnotation(MongoJsonFile.class));
      mongoJsonFile.ifPresent(testFile -> getMongoTemplate(context).ifPresent(template -> {
        template.dropCollection(testFile.collectionName());
      }));
    });
  }


  private Optional<MongoTemplate> getMongoTemplate(ExtensionContext context) {
    return context.getTestClass().map(testClass -> {
          if (MongoTemplateProvider.class.isAssignableFrom(testClass)) {
            return context.getTestInstance()
                .map(testInstance -> ((MongoTemplateProvider) testInstance).getMongoTemplate());
          }
          return Optional.<MongoTemplate>empty();
        })
        .orElse(Optional.empty());
  }
}