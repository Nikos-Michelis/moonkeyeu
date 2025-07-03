package com.moonkeyeu.etl.api.service;

import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import com.moonkeyeu.etl.api.dto.clean.images.*;
import com.moonkeyeu.etl.api.service.fetch.FetchImageService;
import com.moonkeyeu.etl.api.dto.clean.media.MissionPatches;
import com.moonkeyeu.etl.api.dto.clean.pads.LaunchPad;
import com.moonkeyeu.etl.api.utils.data.CustomBatchIterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class S3StorageService {

    private final CsvService csvService;
   // private final boolean skipUpload = false;
    @Value("${aws.s3.buckets.root}")
    private String s3KeyValue;
    private final FetchImageService fetchImageService;
    private final int BATCH_SIZE = 500;

    private final Map<Class<? extends CsvEntity>, String> entityToS3KeyMap = Map.of(
            RocketImages.class, "/rockets/",
            LaunchRocketImages.class, "/launch/",
            LaunchPad.class, "/pads-locations/",
            LauncherImages.class, "/launchers/",
            SpacecraftImages.class, "/spacecraft/",
            AstronautImages.class, "/astronauts/",
            AgenciesImages.class, "/agencies/",
            MissionPatches.class, "/missions-patches/",
            ProgramsImages.class, "/programs/"
    );

    @Autowired
    public S3StorageService(
            CsvService csvService,
            FetchImageService fetchImageService
    ) {
        this.csvService = csvService;
        this.fetchImageService = fetchImageService;
    }

    public void saveMediaToS3(List<CsvEntity> resultList, String bucketName, String outputFileName,
                              String[] headers, Class<? extends CsvEntity> type, boolean skipUpload) {

        CustomBatchIterator.batchStreamOf(resultList.stream(), BATCH_SIZE)
                .forEach(batch -> processBatch(batch, bucketName, outputFileName, headers, type, skipUpload));
    }

    private void processBatch(List<CsvEntity> batch, String bucketName, String outputFileName,
                              String[] headers, Class<? extends CsvEntity> type, boolean skipUpload) {
        List<CsvEntity> updatedEntities = new ArrayList<>();
        batch.forEach(entity -> {
            if (entity instanceof ImageEntity) {
                updateImageUrl((ImageEntity) entity, bucketName, skipUpload);
            }
            updatedEntities.add(entity);
        });
        writeToCsv(updatedEntities, outputFileName, headers, type);
    }

    private void updateImageUrl(ImageEntity entity, String bucketName, boolean skipUpload) {
        try {
            String s3Key = entityToS3KeyMap.get(entity.getClass()) != null ? s3KeyValue + entityToS3KeyMap.get(entity.getClass()) : null;
            if (s3Key != null) {
                String imageUrl = fetchImageService.saveToS3(entity.getImageUrl(), bucketName, s3Key, skipUpload);
                entity.setImageUrl(imageUrl);
            }
        } catch (IOException  e) {
            log.error("Error processing batch: ", e);
        }
    }

    private void writeToCsv(List<CsvEntity> updatedEntities, String outputFileName,
                               String[] headers, Class<? extends CsvEntity> type) {
        try {
            csvService.writeCSVInBatches(updatedEntities, outputFileName, headers, type, BATCH_SIZE);
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            log.error("Error processing batch: ", e);
        }
    }
}
