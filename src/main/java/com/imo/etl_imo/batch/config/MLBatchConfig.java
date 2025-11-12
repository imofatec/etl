package com.imo.etl_imo.batch.config;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.pojo.ProgressWithDetails;
import com.imo.etl_imo.batch.reader.MongoAggregationItemReader;
import com.imo.etl_imo.batch.reader.ProgressDetailsReader;
import com.mongodb.client.MongoClients;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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
  public ItemReader<ProgressWithDetails> progressPojoReader(
          @Qualifier("sourceMongoTemplate") MongoTemplate sourceMongoTemplate) {

    return new MongoAggregationItemReader(sourceMongoTemplate, 100);
  }

  @Bean(name = "completeProgressDetailsReader")
  @Primary
  public ItemReader<ProgressDetails> completeProgressDetailsReader(
          @Qualifier("progressPojoReader") ItemReader<ProgressWithDetails> progressPojoReader) {

    return new ProgressDetailsReader(progressPojoReader);
  }

}
