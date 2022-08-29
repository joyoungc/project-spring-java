
package io.joyoungc.batch.flowjob;

import io.joyoungc.batch.example.item.ExampleItemReader;
import io.joyoungc.batch.example.item.ExampleItemWriter;
import io.joyoungc.batch.example.tasklet.JobChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(flowStep1())
                .on("FAILED").stop()
                .from(flowStep1()).on("*").to(flowStep2())
                .end()
                .build();
    }

    @Bean
    public Step flowStep1() {
        return stepBuilderFactory.get("flowStep1")
                .tasklet(new JobChecker())
                .build();
    }

    @Bean
    public Step flowStep2() {
        return stepBuilderFactory.get("flowStep2")
                .<String,String>chunk(5)
                .reader(new ExampleItemReader())
                .writer(new ExampleItemWriter())
                .build();
    }

}

