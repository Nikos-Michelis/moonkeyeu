package com.moonkeyeu.etl.api.service;

public interface TaskExecutorService {
    void fetchLatestData();
    void fetchLatestAgenciesData();
    void bulkProcessing();
    void fetchAllLatestData();
}
