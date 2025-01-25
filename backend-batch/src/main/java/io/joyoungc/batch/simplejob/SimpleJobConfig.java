
package io.joyoungc.batch.simplejob;

import io.joyoungc.batch.example.item.*;
import io.joyoungc.batch.simplejob.item.SimpleJobParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SimpleJobConfig {

    private final SimpleJobParameter jobParameter;
    private static final String JOB_NAME = "simple";

    @Bean
    @JobScope
    public SimpleJobParameter jobParameter() {
        return new SimpleJobParameter();
    }

    @Bean(name = JOB_NAME + "Job")
    public Job simpleJob(JobRepository jobRepository, @Qualifier("simpleStep1") Step simpleStep1) {
        return new JobBuilder(JOB_NAME + "Job", jobRepository)
                .preventRestart()
                .start(simpleStep1)
                .build();

    }

//    @Bean(name = "exampleJob")
//    public Job exampleJob() {
//        final JobBuilder jobBuilder = jobFactory.get("exampleJob");
//        jobBuilder.incrementer(new RunIdIncrementer());

//        final SimpleJobBuilder simpleJobBuilder = jobBuilder.start(exampleStep1());
//        simpleJobBuilder.on("CUSTOM_EXIT").to(exampleStep2());
//        simpleJobBuilder.next(exampleStep3());
//        return simpleJobBuilder.build();

//        return jobFactory.get("exampleJob")
//                .incrementer(new RunIdIncrementer())
//                .start(simpleStep1())
//                .on("*").to(exampleStep2())
//                .next(exampleStep3())
//                .from(exampleStep1()).on("FAILED").to(exampleStep3())
//                .end()
//                .build();
//    }

    @Bean
    @JobScope
    public Step simpleStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(JOB_NAME + "Step1", jobRepository)
                .<String, String>chunk(5, transactionManager)
                .reader(exampleItemReader())
                .processor(exampleItemProcessor())
                .writer(exampleItemWriter())
//                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @StepScope
    public ExampleStep1ItemReader exampleItemReader() {
        return new ExampleStep1ItemReader(jobParameter);
    }

    @Bean
    @StepScope
    public ExampleStep1ItemProcessor exampleItemProcessor() {
        return new ExampleStep1ItemProcessor(jobParameter);
    }

    @Bean
    @StepScope
    public ExampleStep1ItemWriter exampleItemWriter() {
        return new ExampleStep1ItemWriter(jobParameter);
    }


    @Bean
    @JobScope
    public Step exampleStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("exampleStep2", jobRepository)
                .<String, String>chunk(3, transactionManager)
                .reader(new ExampleStep2ItemReader())
                .writer(new ExampleStep2ItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @JobScope
    public Step exampleStep3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("exampleStep3", jobRepository)
                .<String, String>chunk(3, transactionManager)
                .reader(exampleItemReader())
                .processor(exampleItemProcessor())
                .writer(exampleItemWriter())
                .allowStartIfComplete(true)
                .build();

    }

}
