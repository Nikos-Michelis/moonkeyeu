package com.moonkeyeu.etl.api.service.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.moonkeyeu.etl.api.service.CsvService;
import com.moonkeyeu.etl.api.utils.data.CustomBatchIterator;
import com.moonkeyeu.etl.api.utils.data.DataCleanerHandler;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvServiceImpl implements CsvService {

    private final CsvMapper csvMapper;
    private final DataCleanerHandler dataCleanerHandler;

    public String[] getHeaders(Class<? extends CsvEntity> type) {
        Field[] fields = type.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames.toArray(new String[0]);
    }
    private Map<String, String> mapHeadersToValues (CsvEntity entity, String[] headers) throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> map = new LinkedHashMap<>();
        for (String header : headers) {
            Field field = entity.getClass().getDeclaredField(header);
            field.setAccessible(true);
            Object value = field.get(entity);
            value = dataCleanerHandler.handleSpecialCharacters(value);
            map.put(header, (String) value);
        }
        return map;
    }
    public List<CsvEntity> loadCSV(String fileName, Class<? extends CsvEntity> type) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            log.warn("File not found: " + fileName);
            return Collections.emptyList();
        }
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            MappingIterator<CsvEntity> it = csvMapper.readerFor(type).with(schema).readValues(reader);
            return it.readAll();
        } catch (IOException e) {
            log.error("Error reading CSV file: " + e.getMessage(), e);
            throw e;
        }
    }
    public void writeCSVInBatches(List<CsvEntity> entities, String outputFileName, String[] headers, Class<? extends CsvEntity> type, int batchSize)
            throws IOException, NoSuchFieldException, IllegalAccessException {

        boolean writeHeader = !new File(outputFileName).exists() || new File(outputFileName).length() == 0;
        CsvSchema schema = CsvSchema.builder()
                .addColumns(Arrays.asList(headers), CsvSchema.ColumnType.STRING)
                .setUseHeader(writeHeader)
                .build();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
             CsvGenerator generator = csvMapper.getFactory().createGenerator(writer)) {

            generator.setSchema(schema);
            CustomBatchIterator<CsvEntity> batchIterator = new CustomBatchIterator<>(entities.iterator(), batchSize);

            while (batchIterator.hasNext()) {
                List<CsvEntity> batch = batchIterator.next();
                for (CsvEntity entity : batch) {
                    csvMapper.writer(schema).writeValue(generator, mapHeadersToValues(entity, headers));
                }
                writer.flush();
            }

        } catch (IOException e) {
            log.error("Error writing to CSV file: " + e.getMessage(), e);
            throw e;
        }
    }

    public void deleteAllFiles(String folderPath, boolean deleteOnlyCsv) {
        Path folder = Paths.get(folderPath);
        if (Files.exists(folder) && Files.isDirectory(folder)) {
            try (Stream<Path> files = Files.walk(folder)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> !deleteOnlyCsv || file.toString().toLowerCase().endsWith(".csv"))
                        .forEach(this::deleteFile);

            } catch (IOException e) {
                log.error("An error occurred while reading the directory: " + folderPath, e);
            }
        } else {
            log.error("The specified path is not a directory or does not exist: " + folderPath);
        }
    }
    private void deleteFile(Path file) {
        try {
            boolean deleted = Files.deleteIfExists(file);
            if (deleted) {
                log.debug("Deleted file: " + file.getFileName());
            } else {
                log.error("Failed to delete file: " + file.getFileName());
            }
        } catch (IOException e) {
            log.error("Error deleting file: " + file.getFileName(), e);
        }
    }
}
