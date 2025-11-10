package com.imo.etl_imo.config;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.pojo.ProgressPojo;
import com.imo.etl_imo.reader.ProgressDetailsReader;
import com.imo.etl_imo.repository.CourseRepository;
import com.imo.etl_imo.repository.UserRepository;
import com.mongodb.client.MongoClients;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@EnableMongoRepositories(
    basePackages = "com.imo.etl_imo.repository",
    mongoTemplateRef = "sourceMongoTemplate"
)
public class MLBatchConfig {

  @Bean(name = "sourceMongoClient")
  public MongoClient SourceMongoClient(@Value("${source.mongodb.uri}") String sourceMongoUri) {
    return MongoClients.create(sourceMongoUri);
  }

  @Bean(name = "mainMongoClient")
  @Primary
  public MongoClient mainMongoClient(@Value("${spring.data.mongodb.uri}") String mainMongoUri) {
    return MongoClients.create(mainMongoUri);
  }

  @Bean(name = "sourceMongoTemplate")
  @Primary
  public MongoTemplate sourceMongoTemplate(@Qualifier(value = "sourceMongoClient") MongoClient sourceMongoClient) {
    return new MongoTemplate(sourceMongoClient, "imo");
  }

  @Bean(name = "outputMongoTemplate")
  public MongoTemplate outputMongoTemplate(@Qualifier(value = "sourceMongoClient") MongoClient sourceMongoClient) {
    return new MongoTemplate(sourceMongoClient, "imo");
  }

  @Bean(name = "mongoTemplate")
  public MongoTemplate mongoTemplate(@Qualifier(value = "sourceMongoClient") MongoClient sourceMongoClient) {
    return new MongoTemplate(sourceMongoClient, "imo");
  }

  @Bean(name = "mainMongoTemplate")
  public MongoTemplate mainMongoTemplate(@Qualifier(value = "mainMongoClient") MongoClient mainMongoClient) {
    return new MongoTemplate(mainMongoClient, "imo");
  }

  @Bean(name = "progressPojoReader")
  public ItemReader<ProgressPojo> progressPojoReader(
          @Qualifier("sourceMongoTemplate") MongoTemplate sourceMongoTemplate) {

    Map<String, Sort.Direction> sorts = new HashMap<>();
    sorts.put("createdAt", Sort.Direction.DESC);

    return new MongoPagingItemReaderBuilder<ProgressPojo>()
        .name("progressPojoReader")
        .template(sourceMongoTemplate)
        .collection("progress")
        .jsonQuery("{}")
        .targetType(ProgressPojo.class)
        .sorts(sorts)
        .pageSize(100)
        .build();
  }

  @Bean(name = "completeProgressDetailsReader")
  @Primary
  public ItemReader<ProgressDetails> completeProgressDetailsReader(
          @Qualifier("sourceMongoTemplate") MongoTemplate sourceMongoTemplate,
          @Qualifier("progressPojoReader") ItemReader<ProgressPojo> progressPojoReader) {

    MongoRepositoryFactory factory = new MongoRepositoryFactory(sourceMongoTemplate);
    
    UserRepository userRepository = factory.getRepository(UserRepository.class);
    CourseRepository courseRepository = factory.getRepository(CourseRepository.class);
    
    return new ProgressDetailsReader(
        progressPojoReader,
        userRepository,
        courseRepository
    );
  }

}
