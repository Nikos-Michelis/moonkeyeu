package com.moonkeyeu.etl.api.configuration.batch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonkeyeu.etl.api.configuration.files.CsvRawData;
import com.moonkeyeu.etl.api.configuration.files.RootConfig;
import com.moonkeyeu.etl.api.service.CsvService;
import com.moonkeyeu.etl.api.service.DataManagerService;
import com.moonkeyeu.etl.api.service.fetch.FetchDataService;
import com.moonkeyeu.etl.api.utils.JsonFileReader;
import com.moonkeyeu.etl.api.utils.mapper.JsonToEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
@Slf4j
public class TasksConfig {
    private final JobRepository jobRepository;
    private final DataManagerService dataManagerService;
    private final PlatformTransactionManager platformTransactionManager;
    private final JsonFileReader jsonFileReader;
    private final FetchDataService fetchDataService;
    private final JsonToEntityMapper jsonToEntityMapper;
    private final ObjectMapper objectMapper;
    private final CsvService csvService;
    private final RootConfig rootConfig;
    private final CsvRawData csvRawData;
    private final String agenciesUrl = "https://ll.thespacedevs.com/2.3.0/agencies/?mode=detailed&limit=100";
    private final String launchesUlr = "https://ll.thespacedevs.com/2.3.0/launches/?mode=detailed&limit=100";
    private final String baseUrl = "https://ll.thespacedevs.com/2.3.0/launches/";

    @Bean
    public Step cleanFolders() {
        return new StepBuilder("initializeFolders", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();
                    boolean skipNonCsv = Boolean.parseBoolean((String) jobParametersMap.getOrDefault("skipNonCsv", "false"));
                    csvService.deleteAllFiles(rootConfig.getRawRootFolder(), skipNonCsv);
                    csvService.deleteAllFiles(rootConfig.getCleanRootFolder(), skipNonCsv);
                    log.info("Successfully cleaned folders.");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public Step fetchLatestDataStep() {
        return new StepBuilder("fetchLatestDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                    String windowsStart = now.toLocalDate().minusMonths(1).toString();
                    String windowEnd = now.plusMonths(7).toLocalDate().toString();
                    String url = String.format("%s?mode=detailed&limit=100&ordering=-last_updated&net__gte=%s&net__lte=%s",
                            baseUrl, windowsStart, windowEnd);
                    fetchDataService.fetchData(url, csvRawData.JSON_SOURCE_FILE).block();
                    log.info("Successfully fetched latest data. Between " + windowsStart + " and " + windowEnd );
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public Step fetchAllLatestDataStep() {
        return new StepBuilder("fetchLatestDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                    String windowsStart = now.toLocalDate().minusMonths(1).toString();
                    String url = String.format("%s?mode=detailed&limit=100&ordering=-last_updated&net__gte=%s",
                            baseUrl, windowsStart);
                    fetchDataService.fetchData(url, csvRawData.JSON_SOURCE_FILE).block();
                    log.info("Successfully fetched all latest data. window start: " + windowsStart );
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step fetchAgenciesDataStep() {
        return new StepBuilder("fetchAgenciesStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    fetchDataService.fetchData(agenciesUrl, csvRawData.JSON_AGENCIES_SOURCE_FILE).block();
                    log.info("Successfully fetched JSON data.");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public Step fetchLaunchesDataStep() {
        return new StepBuilder("fetchLaunchesStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    fetchDataService.fetchData(launchesUlr, csvRawData.JSON_SOURCE_FILE).block();
                    log.info("Successfully fetched JSON data.");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step readAgenciesJsonDataStep() {
        return new StepBuilder("readAgenciesJsonDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    JsonNode rootNode = jsonFileReader.loadJsonFile(csvRawData.JSON_AGENCIES_SOURCE_FILE);
                    jsonToEntityMapper.processAgenciesResponse(rootNode, objectMapper);
                    log.info("Successfully read JSON data from file: {}", csvRawData.JSON_SOURCE_FILE);
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public Step readLaunchesJsonDataStep() {
        return new StepBuilder("readLaunchesJsonDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    JsonNode rootNode = jsonFileReader.loadJsonFile(csvRawData.JSON_SOURCE_FILE);
                    jsonToEntityMapper.processLaunchResponse(rootNode, objectMapper);
                    log.info("Successfully read JSON data from file: {}", csvRawData.JSON_SOURCE_FILE);
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step cleanRawDataStep() {
        return new StepBuilder("cleanRawDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();
                    boolean skipUpload = Boolean.parseBoolean((String) jobParametersMap.getOrDefault("skipUpload", "false"));
                    dataManagerService.cleanRawData(skipUpload);
                    log.info("Successfully cleaned raw data.");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step importDataStep() {
        return new StepBuilder("importDataStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    dataManagerService.importData();
                    log.info("Data Successfully imported.");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .transactionManager(platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job fetchAllData() {
        return new JobBuilder("fetchAllData", jobRepository)
                .start(cleanFolders())
                .start(fetchAgenciesDataStep())
                .next(fetchLaunchesDataStep())
                .build();
    }

    @Bean
    public Job runBulkInsertJob() {
        return new JobBuilder("bulkInsertJob", jobRepository)
                .start(cleanFolders())
                .next(readAgenciesJsonDataStep())
                .next(readLaunchesJsonDataStep())
                .next(cleanRawDataStep())
                .next(importDataStep())
                .build();
    }

    @Bean
    public Job updateAgenciesJob() {
        return new JobBuilder("updateAgenciesJob", jobRepository)
                .start(cleanFolders())
                .next(fetchAgenciesDataStep())
                .next(readAgenciesJsonDataStep())
                .next(cleanRawDataStep())
                .next(importDataStep())
                .build();
    }

    @Bean
    public Job runDailyJob() {
        return new JobBuilder("dailyJob", jobRepository)
                .start(cleanFolders())
                .next(fetchLatestDataStep())
                .next(readLaunchesJsonDataStep())
                .next(cleanRawDataStep())
                .next(importDataStep())
                .build();
    }
     @Bean
    public Job runMidnightJob() {
        return new JobBuilder("midnightJob", jobRepository)
                .start(cleanFolders())
                .next(fetchAllLatestDataStep())
                .next(readLaunchesJsonDataStep())
                .next(cleanRawDataStep())
                .next(importDataStep())
                .build();
    }
}