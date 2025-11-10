package com.imo.etl_imo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job mlProcessingJob;

    public BatchJobRunner(JobLauncher jobLauncher, Job mlProcessingJob) {
        this.jobLauncher = jobLauncher;
        this.mlProcessingJob = mlProcessingJob;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting ML Processing Job...");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        var execution = jobLauncher.run(mlProcessingJob, jobParameters);

        System.out.println("Job completed with status: " + execution.getStatus());

    }
}
