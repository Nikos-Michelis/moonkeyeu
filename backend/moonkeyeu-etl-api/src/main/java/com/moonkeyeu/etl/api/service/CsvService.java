package com.moonkeyeu.etl.api.service;

import com.moonkeyeu.etl.api.dto.clean.CsvEntity;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface CsvService {
    String[] getHeaders(Class<? extends CsvEntity> type);
    List<CsvEntity> loadCSV(String fileName, Class<? extends CsvEntity> type) throws IOException;
    void writeCSVInBatches(List<CsvEntity> entities,
                           String outputFileName, String[] headers,
                           Class<? extends CsvEntity> type,
                           int batchSize) throws IOException, NoSuchFieldException, IllegalAccessException;
    void deleteAllFiles(String folderPath, boolean deleteOnlyCsv);
}
