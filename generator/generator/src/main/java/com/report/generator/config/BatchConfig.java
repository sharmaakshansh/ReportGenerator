package com.report.generator.config;

import com.report.generator.Item.CsvItemProcessor;
import com.report.generator.Item.CsvItemReader;
import com.report.generator.Item.CsvItemWriter;
import com.report.generator.entity.InputEntity;
import com.report.generator.entity.OutputEntity;
import com.report.generator.entity.ReferenceEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private CsvItemReader csvItemReader;

    @Autowired
    private CsvItemWriter csvItemWriter;

    private List<ReferenceEntity> referenceEntities = new ArrayList<>();

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(referenceStep())
                .next(inputStep())
                .build();
    }

    @Bean
    public Step referenceStep() {
        return new StepBuilder("referenceStep", jobRepository)
                .<ReferenceEntity, ReferenceEntity>chunk(100, transactionManager)
                .reader(csvItemReader.referenceReader())
                .processor(item -> item)  // No processing needed for reference data
                .writer(referenceData -> {
                    List<ReferenceEntity> refList = new ArrayList<>();
                    referenceData.forEach(refList::add);
                    this.referenceEntities = refList;
                })
                .build();
    }

    @Bean
    public Step inputStep() {
        return new StepBuilder("inputStep", jobRepository)
                .<InputEntity, OutputEntity>chunk(1000, transactionManager)
                .reader(csvItemReader.inputReader())
                .processor(new CsvItemProcessor(this.referenceEntities))
                .writer(csvItemWriter.writer())
                .build();
    }

    @Bean
    public CsvItemReader csvItemReader() {
        return new CsvItemReader();
    }

    @Bean
    public CsvItemWriter csvItemWriter() {
        return new CsvItemWriter();
    }
}
