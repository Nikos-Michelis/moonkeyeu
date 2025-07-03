package com.moonkeyeu.etl.api.service;

import com.moonkeyeu.etl.api.dto.clean.CsvEntity;

import java.io.IOException;

public interface DataManagerService {
    void processData(String inputFile, String outputFile, Class<? extends CsvEntity> type, boolean isS3Enable, boolean skipUpload) throws IOException, NoSuchFieldException, IllegalAccessException;
    void cleanRawData(boolean skipUpload) throws IOException, NoSuchFieldException, IllegalAccessException;
    void importData() throws IOException, IllegalAccessException;

}
