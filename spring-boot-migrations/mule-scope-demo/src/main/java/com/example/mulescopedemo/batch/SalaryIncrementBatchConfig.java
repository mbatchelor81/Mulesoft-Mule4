package com.example.mulescopedemo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SalaryIncrementBatchConfig {

    @Bean
    public JdbcCursorItemReader<Map<String, Object>> empReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Map<String, Object>>()
            .name("empReader")
            .dataSource(dataSource)
            .sql("SELECT emp_id, emp_name, emp_salary, emp_appr_per FROM emp")
            .rowMapper(new ColumnMapRowMapper())
            .build();
    }

    @Bean
    public SalaryIncrementProcessor salaryIncrementProcessor() {
        return new SalaryIncrementProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Map<String, Object>> empBackupWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
            .sql("INSERT INTO emp_backup (emp_id, emp_name, emp_salary, increment_sal) VALUES (:emp_id, :emp_name, :emp_salary, :increment_sal)")
            .dataSource(dataSource)
            .columnMapped()
            .build();
    }

    @Bean
    public Step salaryIncrementStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     JdbcCursorItemReader<Map<String, Object>> empReader,
                                     SalaryIncrementProcessor processor,
                                     JdbcBatchItemWriter<Map<String, Object>> empBackupWriter) {
        return new StepBuilder("salaryIncrementStep", jobRepository)
            .<Map<String, Object>, Map<String, Object>>chunk(4, transactionManager)
            .reader(empReader)
            .processor(processor)
            .writer(empBackupWriter)
            .build();
    }

    @Bean
    public Job salaryIncrementBatch(JobRepository jobRepository, Step salaryIncrementStep) {
        return new JobBuilder("salaryIncrementBatch", jobRepository)
            .start(salaryIncrementStep)
            .build();
    }

    public static class SalaryIncrementProcessor implements org.springframework.batch.item.ItemProcessor<Map<String, Object>, Map<String, Object>> {
        @Override
        public Map<String, Object> process(Map<String, Object> item) {
            Map<String, Object> result = new HashMap<>(item);
            double salary = item.get("emp_salary") != null ? ((Number) item.get("emp_salary")).doubleValue() : 0;
            double apprPer = item.get("emp_appr_per") != null ? ((Number) item.get("emp_appr_per")).doubleValue() : 0;
            double incrementSal = salary * ((apprPer + 100) / 100);
            result.put("increment_sal", incrementSal);
            return result;
        }
    }
}
