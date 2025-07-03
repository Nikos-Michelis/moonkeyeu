package com.moonkeyeu.etl.api.service;

import com.moonkeyeu.etl.api.configuration.files.ExtractImages;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.ImageEntity;
import com.moonkeyeu.etl.api.dto.clean.images.*;
import com.moonkeyeu.etl.api.dto.clean.media.MissionPatches;
import com.moonkeyeu.etl.api.dto.clean.pads.LaunchPad;
import com.moonkeyeu.etl.api.service.fetch.FetchImageService;
import com.moonkeyeu.etl.api.utils.data.CustomBatchIterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LocalStorageService {

    private final CsvService csvService;
    private final FetchImageService fetchImageService;
    private final ExtractImages extractImages;

    @Value("${application.backend.url}")
    private String storageUrl;

    private final Map<Class<? extends CsvEntity>, String> entityToLocalKeyMap = Map.of(
            RocketImages.class, "rockets/",
            LaunchRocketImages.class, "launch/",
	        PadImages.class, "pads/",
            LaunchPad.class, "pads-locations/",
            LauncherImages.class, "launchers/",
            SpacecraftImages.class, "spacecraft/",
            AstronautImages.class, "astronauts/",
            AgenciesImages.class, "agencies/",
            MissionPatches.class, "missions-patches/",
            ProgramsImages.class, "programs/"
    );

    private static final int BATCH_SIZE = 200;

    @Autowired
    public LocalStorageService(
            FetchImageService fetchImageService,
            CsvService csvService,
            ExtractImages extractImages
    ) {
        this.fetchImageService = fetchImageService;
        this.csvService = csvService;
        this.extractImages = extractImages;
    }

    public void saveMediaLocal(List<CsvEntity> resultList, String outputFileName,
                               String[] headers, Class<? extends CsvEntity> type) {
        CustomBatchIterator.batchStreamOf(resultList.stream(), BATCH_SIZE)
                .forEach(batch -> processBatch(batch, outputFileName, headers, type));
    }

    private void processBatch(List<CsvEntity> batch, String outputFileName,
                              String[] headers, Class<? extends CsvEntity> type) {
        List<CsvEntity> updatedEntities = new ArrayList<>();
        batch.forEach(entity -> {
            if (entity instanceof ImageEntity) {
                updateImageUrl((ImageEntity) entity);
            }
            updatedEntities.add(entity);
        });
        writeToCsv(updatedEntities, outputFileName, headers, type);
    }

    private void updateImageUrl(ImageEntity entity) {
        try {
            String localDirPath = extractImages.buildPath(extractImages.getRootPathImages(), entityToLocalKeyMap.get(entity.getClass()));
            if (localDirPath != null) {
                URL url = new URL(storageUrl + entityToLocalKeyMap.get(entity.getClass()));
                String imageUrl = fetchImageService.saveLocal(entity.getImageUrl(), localDirPath, String.valueOf(url));
                entity.setImageUrl(imageUrl);
            }
        } catch (IOException e) {
            log.error("Error processing batch: ", e);
        }
    }

    private void writeToCsv(List<CsvEntity> updatedEntities, String outputFileName,
                            String[] headers, Class<? extends CsvEntity> type) {
        try {
            csvService.writeCSVInBatches(updatedEntities, outputFileName, headers, type, BATCH_SIZE);
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            log.error("Error writing to CSV: ", e);
        }
    }
}
