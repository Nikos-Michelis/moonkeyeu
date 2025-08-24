package com.moonkeyeu.etl.api.service.writer;

import com.moonkeyeu.etl.api.utils.data.CustomBatchIterator;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JsonToCsvService {
    public <T> void writeDataToCSV(List<T> dataList, String fileName, int batchSize) {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("Invalid data list.");
        }
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] header = getHeaders(dataList);
            writeHeaders(csvWriter, file, header);
            writeBatchDataToCSV(dataList, csvWriter, batchSize, header);
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing data to CSV", e);
            e.printStackTrace();
        }
    }
    private <T> String[] getHeaders(List<T> dataList) {
        Set<String> headerSet = new LinkedHashSet<>();
        for (T obj : dataList) {
            if (obj != null) {
                addHeaders(obj.getClass(), headerSet);
            }
        }
        return headerSet.toArray(new String[0]);
    }
    private void writeHeaders(CSVWriter csvWriter, File file, String[] header) throws IOException {
        boolean isFileEmpty = !file.exists() || file.length() == 0;
        if (isFileEmpty) {
            csvWriter.writeNext(header);
        }
    }
    private <T> void writeBatchDataToCSV(List<T> dataList, CSVWriter csvWriter, int batchSize, String[] header) {
        CustomBatchIterator.batchStreamOf(dataList.stream(), batchSize)
                .forEach(batch -> {
                    List<String[]> batchData = batch.stream().map(obj -> getObjectData(obj, header)).collect(Collectors.toList());
                    csvWriter.writeAll(batchData);
                });
    }
    private <T> String[] getObjectData(T obj, String[] header) {
        Map<String, String> fieldMap = getObjectFields(obj);
        String[] data = new String[header.length];
        for (int j = 0; j < header.length; j++) {
            data[j] = fieldMap.getOrDefault(header[j], null);
        }
        return data;
    }
    private Map<String, String> getObjectFields(Object obj) {
        Map<String, String> fieldMap = new LinkedHashMap<>();
        if (obj == null) {
            return fieldMap;
        }

        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (isPrimitiveOrString(field.getType())) {
                    handlePrimitiveOrStringField(field, obj, fieldMap, fieldName);
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    handleCollectionField(field, obj, fieldMap);
                } else {
                    handleNestedObjectField(field, obj, fieldMap);
                }
            } catch (InaccessibleObjectException e) {
                log.info("Skipping inaccessible field: " + field.getName());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return fieldMap;
    }
    private void handlePrimitiveOrStringField(Field field, Object obj, Map<String, String> fieldMap, String fieldName) throws IllegalAccessException {
        Object value = field.get(obj);
        fieldMap.put(fieldName, value != null ? value.toString() : null);
    }
    private void handleNestedObjectField(Field field, Object obj, Map<String, String> fieldMap) throws IllegalAccessException {
        Object value = field.get(obj);
        if (value != null) {
            Map<String, String> nestedFields = getObjectFields(value);
            fieldMap.putAll(nestedFields);
        }
    }
    private void handleCollectionField(Field field, Object obj, Map<String, String> fieldMap) throws IllegalAccessException {
        Collection<?> collection = (Collection<?>) field.get(obj);
        if (collection != null) {
            for (Object item : collection) {
                if (item != null) {
                    fieldMap.putAll(getObjectFields(item));
                }
            }
        }
    }
    private void addHeaders(Class<?> clazz, Set<String> headerSet) {
        for (Field field : clazz.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                    java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            String fieldName = field.getName();
            if (isPrimitiveOrString(field.getType())) {
                headerSet.add(fieldName);
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                headerSet.add(fieldName);
                Class<?> collectionType = getCollectionItemType(field);
                if (collectionType != null) {
                    addHeaders(collectionType, headerSet);
                }
            } else {
                addHeaders(field.getType(), headerSet);
            }
        }
    }
    private Class<?> getCollectionItemType(Field field) {
        try {
            Class<?> clazz = field.getType();
            if (Collection.class.isAssignableFrom(clazz)) {
                return (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private boolean isPrimitiveOrString(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class) || Number.class.isAssignableFrom(type)
                || type.equals(Boolean.class) || type.equals(Character.class);
    }
}
