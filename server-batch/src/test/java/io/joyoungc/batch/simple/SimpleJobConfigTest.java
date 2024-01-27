package io.joyoungc.batch.simple;

import io.joyoungc.batch.ServerBatchIntegrationTest;
import io.joyoungc.batch.simplejob.SimpleJobConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig(SimpleJobConfig.class)
class SimpleJobConfigTest extends ServerBatchIntegrationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void execute_SimpleJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "it's me")
                .addLong("age", 40L)
                .toJobParameters();
        jobLauncherTestUtils.launchJob(jobParameters);
    }

}