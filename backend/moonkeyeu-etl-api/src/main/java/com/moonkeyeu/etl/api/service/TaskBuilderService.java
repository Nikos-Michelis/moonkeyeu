package com.moonkeyeu.etl.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class TaskBuilderService {
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    public TaskBuilderService(JobLauncher jobLauncher,
                                   JobExplorer jobExplorer,
                                   JobRepository jobRepository) {
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.jobRepository = jobRepository;
    }
    private JobParameters createJob(String jobName, boolean skipNonCsv, boolean skipUpload){
        return new JobParametersBuilder()
                .addString("jobName", jobName)
                .addString("skipNonCsv", Boolean.toString(skipNonCsv))
                .addString("skipUpload", Boolean.toString(skipUpload))
                .addString("uniqueId", UUID.randomUUID().toString())
                .toJobParameters();
    }
    private void invalidateJob(String jobName){
        Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(jobName);
        if (!jobExecutions.isEmpty()) {
            for (JobExecution jobExecution : jobExecutions) {
                if (jobExecution.getStatus().isRunning()) {
                    log.debug("A job is already running with ID: {}", jobExecution.getId());
                    jobExecution.setStatus(BatchStatus.STOPPED);
                    jobRepository.update(jobExecution);
                }
            }
        }
    }
    public JobExecution jobLauncher(String jobName, boolean skipNonCsv, boolean skipUpload, Job dataProcessingJob)
            throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobParameters jobParameters = createJob(jobName, skipNonCsv, skipUpload);
        invalidateJob(jobName);
        JobExecution jobExecution = jobLauncher.run(dataProcessingJob, jobParameters);
        log.debug("Started new job execution for {}: {}", jobName, jobParameters);
        return jobExecution;
    }
}
