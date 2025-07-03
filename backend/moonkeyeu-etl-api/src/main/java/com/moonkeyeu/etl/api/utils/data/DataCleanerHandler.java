package com.moonkeyeu.etl.api.utils.data;

import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataCleanerHandler {
    private static final String UNKNOWN_VALUE = "Unknown";
    public String handleSpecialCharacters(Object value) {
        if (value == null) {
            return null;
        }
        String strValue = value.toString().trim();
        return strValue.isBlank() ? null : "???".equals(strValue) ? UNKNOWN_VALUE : strValue;
    }
    public <T> void nullIfEmpty(T entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object originalValue = field.get(entity);

            if (originalValue instanceof String str && str.isEmpty()) {
                field.set(entity, null);
            }
        }
    }
    public List<CsvEntity> getUniqueEntities(List<CsvEntity> entitiesList) {
        Set<String> seenIds = new HashSet<>();
        return entitiesList.stream()
                .filter(entity -> {
                    String id = entity.getPrimaryKey();
                    return id != null
                            && !id.isEmpty()
                            && seenIds.add(id);
                })
                .collect(Collectors.toList());
    }
}