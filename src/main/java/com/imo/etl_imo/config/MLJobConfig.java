package com.imo.etl_imo.config;

import com.imo.etl_imo.model.dto.ProgressDetails;
import com.imo.etl_imo.model.dto.AnalyticsDetails;
import com.imo.etl_imo.processor.CompletionProbabilityProcessor;
import com.imo.etl_imo.writer.AnalyticsWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class MLJobConfig {

    private static final int CHUNK_SIZE = 10;

    @Bean
    public Job mlProcessingJob(
            JobRepository jobRepository,
            @Qualifier("mlProcessingStep") Step mlProcessingStep) {
        
        return new JobBuilder("mlProcessingJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(mlProcessingStep)
            .build();
    }

    @Bean(name = "mlProcessingStep")
    public Step mlProcessingStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("completeProgressDetailsReader") ItemReader<ProgressDetails> reader,
            CompletionProbabilityProcessor processor,
            AnalyticsWriter writer) {

        return new StepBuilder("mlProcessingStep", jobRepository)
            .<ProgressDetails, AnalyticsDetails>chunk(CHUNK_SIZE, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
}