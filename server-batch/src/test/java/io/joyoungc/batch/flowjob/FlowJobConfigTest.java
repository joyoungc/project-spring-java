package io.joyoungc.batch.flowjob;

import io.joyoungc.batch.ServerBatchIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig(FlowJobConfig.class)
class FlowJobConfigTest extends ServerBatchIntegrationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void execute_FlowJob_with_JobStop_is_false() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("isJobStop", Boolean.FALSE.toString())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    void execute_FlowJob_with_JobStop_is_true() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("isJobStop", Boolean.TRUE.toString())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.STOPPED);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.STOPPED);
    }

}