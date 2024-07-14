package com.report.generator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/batch")
public class BatchJobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private static final Logger log = LoggerFactory.getLogger(BatchJobController.class);

    @GetMapping("/run")
    public ResponseEntity<String> runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            // Log job execution details for better troubleshooting
            log.info("Job started with ID: {}, status: {}", jobExecution.getJobId(), jobExecution.getStatus());

            return ResponseEntity.ok("Job executed successfully, status: " + jobExecution.getStatus());
        } catch (Exception e) {
            // Log and handle any exceptions that occur during job execution
            log.error("Job execution failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Job execution failed, error: " + e.getMessage());
        }
    }
}
