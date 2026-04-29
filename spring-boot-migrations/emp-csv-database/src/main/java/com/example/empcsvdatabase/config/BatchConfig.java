package com.example.empcsvdatabase.config;

import com.example.empcsvdatabase.model.EmployeeCsv;
import com.example.empcsvdatabase.model.EmployeeMaster;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Value("${csv.file.path:employees.csv}")
    private String csvFilePath;

    @Bean
    public FlatFileItemReader<EmployeeCsv> csvReader() {
        return new FlatFileItemReaderBuilder<EmployeeCsv>()
            .name("employeeCsvReader")
            .resource(new FileSystemResource(csvFilePath))
            .delimited()
            .names("empId", "empName", "empStatus")
            .targetType(EmployeeCsv.class)
            .linesToSkip(1)
            .build();
    }

    @Bean
    public ItemProcessor<EmployeeCsv, EmployeeMaster> csvToEntityProcessor() {
        return csv -> {
            EmployeeMaster entity = new EmployeeMaster();
            entity.setEmpId(Integer.parseInt(csv.getEmpId().trim()));
            entity.setEmpName(csv.getEmpName().trim());
            entity.setEmpStatus(csv.getEmpStatus().trim());
            return entity;
        };
    }

    @Bean
    public JdbcBatchItemWriter<EmployeeMaster> empMasterWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<EmployeeMaster>()
            .sql("INSERT INTO emp_master (emp_id, emp_name, emp_status) VALUES (:empId, :empName, :empStatus)")
            .dataSource(dataSource)
            .beanMapped()
            .build();
    }

    @Bean
    public Step csvImportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                              FlatFileItemReader<EmployeeCsv> reader,
                              ItemProcessor<EmployeeCsv, EmployeeMaster> processor,
                              JdbcBatchItemWriter<EmployeeMaster> writer) {
        return new StepBuilder("csvImportStep", jobRepository)
            .<EmployeeCsv, EmployeeMaster>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Job csvImportJob(JobRepository jobRepository, Step csvImportStep) {
        return new JobBuilder("csvImportJob", jobRepository)
            .start(csvImportStep)
            .build();
    }
}
