package com.moonkeyeu.etl.api.service.impl;

import com.moonkeyeu.etl.api.service.TaskBuilderService;
import com.moonkeyeu.etl.api.service.TaskExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class TaskExecutorServiceImpl implements TaskExecutorService {
    @Qualifier("runDailyJob")
    private final Job runDailyJob;
    @Qualifier("runMidnightJob")
    private final Job runMidnightJob;
    @Qualifier("updateAgenciesJob")
    private final Job updateAgenciesJob;
    @Qualifier("runBulkInsertJob")
    private final Job runBulkInsertJob;

    private final TaskBuilderService taskBuilderService;

    @Autowired
    public TaskExecutorServiceImpl(
            Job runDailyJob,
            Job runMidnightJob, Job updateAgenciesJob,
            Job runBulkInsertJob,
            TaskBuilderService taskBuilderService
    ) {
        this.runDailyJob = runDailyJob;
        this.runMidnightJob = runMidnightJob;
        this.updateAgenciesJob = updateAgenciesJob;
        this.runBulkInsertJob = runBulkInsertJob;
        this.taskBuilderService = taskBuilderService;
    }

    /**
     * enable the s3 bucket to upload the actual images. (skipUpload = false)
     **/
    @Scheduled(cron = "0 0/110 1-22 * * *")
    @Override
    public void fetchLatestData() {
        try {
            boolean skipNonCsv = false;
            boolean skipUpload = true;
            JobExecution dailyJobExecution = taskBuilderService.jobLauncher("dailyJob", skipNonCsv, skipUpload, runDailyJob);
            if (dailyJobExecution.getStatus() != BatchStatus.COMPLETED) {
                log.warn("Daily job failed. Attempting to update agencies job.");
                JobExecution updateAgenciesExecution = taskBuilderService.jobLauncher("updateAgenciesJob", skipNonCsv, skipUpload, updateAgenciesJob);
                if (updateAgenciesExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("Update agencies job completed successfully. Rerunning daily job.");
                    taskBuilderService.jobLauncher("dailyJob", skipNonCsv, skipUpload, runDailyJob);
                } else {
                    log.error("Update agencies job also failed. Aborting workflow.");
                }
            } else {
                log.info("Daily job completed successfully.");
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Unexpected error during fetchLatestData: {}", e.getMessage(), e);
        }
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void fetchAllLatestData() {
        try {
            boolean skipNonCsv = false;
            boolean skipUpload = false;
            JobExecution midnightJobExecution = taskBuilderService.jobLauncher("midnightJob", skipNonCsv, skipUpload, runMidnightJob);
            if (midnightJobExecution.getStatus() != BatchStatus.COMPLETED) {
                log.warn("Midnight job failed. Attempting to update agencies job.");
                JobExecution updateAgenciesExecution = taskBuilderService.jobLauncher("updateAgenciesJob", skipNonCsv, skipUpload, updateAgenciesJob);
                if (updateAgenciesExecution.getStatus() == BatchStatus.COMPLETED) {
                    log.info("Update agencies job completed successfully. Rerunning midnight job.");
                    taskBuilderService.jobLauncher("midnightJob", skipNonCsv, skipUpload, runMidnightJob);
                } else {
                    log.error("Update agencies job also failed. Aborting workflow.");
                }
            } else {
                log.info("Midnight job completed successfully.");
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Unexpected error during fetchLatestData: {}", e.getMessage(), e);
        }
    }

    //@Scheduled(cron="0 0 * * * *")
    @Override
    public void fetchLatestAgenciesData() {
        try {
            taskBuilderService.jobLauncher("updateAgenciesJob", false, false, updateAgenciesJob);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Unexpected error during fetchLatestAgenciesData: {}", e.getMessage(), e);
        }
    }
    /**
     * disable the s3 bucket to upload the actual images and create only the links for the images (skipUpload = true)
     **/
   //@Scheduled(cron = "0 * * * * *")
   //@Scheduled(fixedRate = 7000)
   @Override
    public void bulkProcessing() {
        try {
            taskBuilderService.jobLauncher("bulkInsertJob", true, true, runBulkInsertJob);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("Unexpected error during fetchLatestAgenciesData: {}", e.getMessage(), e);
        }
    }
}
