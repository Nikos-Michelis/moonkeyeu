package com.moonkeyeu.etl.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class JsonFileReader {
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonFileReader(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    public JsonNode loadJsonFile(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            JsonParser parser = objectMapper.getFactory().createParser(inputStream);
            return objectMapper.readTree(parser);
        } catch (IOException e) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
    }
}
