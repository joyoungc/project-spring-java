package io.joyoungc.batch.example.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class JobChecker implements Tasklet, StepExecutionListener {

    private boolean isJobStop;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        String isJobStopStr = (String) chunkContext.getStepContext().getJobParameters().get("isJobStop");
        log.info("## JobChecker execute : {}", isJobStopStr);
        isJobStop = Boolean.parseBoolean(isJobStopStr);
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (isJobStop) {
            return ExitStatus.FAILED;
        } else {
            return ExitStatus.COMPLETED;
        }
    }
}
