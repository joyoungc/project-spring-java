package io.joyoungc.batch.flowjob;

import io.joyoungc.batch.TestBatchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest(classes = {FlowJobConfig.class, TestBatchConfig.class})
class FlowJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void executeFlowJobWithJobStopIsFalse() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("isJobStop", Boolean.FALSE.toString())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    void executeFlowJobWithJobStopIsTrue() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("isJobStop", Boolean.TRUE.toString())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.STOPPED);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.STOPPED);
    }

}