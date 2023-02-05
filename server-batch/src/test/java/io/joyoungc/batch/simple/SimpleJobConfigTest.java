package io.joyoungc.batch.simple;

import io.joyoungc.batch.TestBatchConfig;
import io.joyoungc.batch.simplejob.SimpleJobConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {SimpleJobConfig.class, TestBatchConfig.class})
class SimpleJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void execute_SimpleJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name","it's me")
                .addLong("age", 40L)
                .toJobParameters();
        jobLauncherTestUtils.launchJob(jobParameters);
    }

}