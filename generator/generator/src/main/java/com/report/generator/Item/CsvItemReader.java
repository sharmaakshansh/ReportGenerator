package com.report.generator.Item;

import com.report.generator.entity.InputEntity;
import com.report.generator.entity.ReferenceEntity;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class CsvItemReader {
    public FlatFileItemReader<InputEntity> inputReader() {
        FlatFileItemReader<InputEntity> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input.csv"));
        reader.setLineMapper(new DefaultLineMapper<InputEntity>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("field1", "field2", "field3", "field4", "field5", "refkey1", "refkey2");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<InputEntity>() {{
                setTargetType(InputEntity.class);
            }});
        }});
        return reader;
    }

    public FlatFileItemReader<ReferenceEntity> referenceReader() {
        FlatFileItemReader<ReferenceEntity> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("reference.csv"));
        reader.setLineMapper(new DefaultLineMapper<ReferenceEntity>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("refkey1", "refdata1", "refkey2", "refdata2", "refdata3", "refdata4");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<ReferenceEntity>() {{
                setTargetType(ReferenceEntity.class);
            }});
        }});
        return reader;
    }
}
