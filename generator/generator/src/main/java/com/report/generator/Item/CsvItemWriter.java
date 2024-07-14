package com.report.generator.Item;

import com.report.generator.entity.OutputEntity;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;

public class CsvItemWriter {

    public FlatFileItemWriter<OutputEntity> writer() {
        return new FlatFileItemWriterBuilder<OutputEntity>()
                .name("outputWriter")
                .resource(new FileSystemResource("E:/springboot projects/generator/generator/src/main/resources"))
                .delimited()
                .delimiter(",")
                .names("outfield1", "outfield2", "outfield3", "outfield4", "outfield5")
                .build();
    }

}
