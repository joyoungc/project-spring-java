
package io.joyoungc.batch.flowjob;

import io.joyoungc.batch.example.item.ExampleItemReader;
import io.joyoungc.batch.example.item.ExampleItemWriter;
import io.joyoungc.batch.example.tasklet.JobChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FlowJobConfig {

    @Bean
    public Job flowJob(JobRepository jobRepository,
                       @Qualifier("flowStep1") Step flowStep1,
                       @Qualifier("flowStep2") Step flowStep2) {
        return new JobBuilder("flowJob", jobRepository)
                .start(flowStep1)
                .on("FAILED").stop()
                .from(flowStep1).on("*").to(flowStep2)
                .end()
                .build();
    }

    @Bean
    public Step flowStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("flowStep1", jobRepository)
                .tasklet(new JobChecker(), transactionManager)
                .build();
    }

    @Bean
    public Step flowStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("flowStep2", jobRepository)
                .<String, String>chunk(5, transactionManager)
                .reader(new ExampleItemReader())
                .writer(new ExampleItemWriter())
                .build();
    }

}

