package com.moonkeyeu.core.api.launch.controller;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile({"local"})
@RestController
@RequestMapping("images")
public class ImageController {
    private String agenciesImage;
    private String rocketsImage;
    private String spacecraftImage;
    private String padsImage;
    private String padsImageLocation;
    private String astronautsImage;
    private String launchersImage;
    private String missionPatchesImage;
    private String programImages;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @PostConstruct
    public void init() {
        String userHome = "/app";
        agenciesImage = Paths.get(userHome, "db-images", "agencies").toString();
        rocketsImage = Paths.get(userHome, "db-images", "rockets").toString();
        spacecraftImage = Paths.get(userHome, "db-images", "spacecraft").toString();
        padsImage = Paths.get(userHome, "db-images", "pads").toString();
        padsImageLocation = Paths.get(userHome, "db-images", "pads_locations").toString();
        astronautsImage = Paths.get(userHome, "db-images", "astronauts").toString();
        launchersImage = Paths.get(userHome, "db-images", "launchers").toString();
        missionPatchesImage = Paths.get(userHome, "db-images", "missions_patches").toString();
        programImages = Paths.get(userHome, "db-images", "programs").toString();
    }

    @GetMapping(value = "agencies/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.AGENCIES_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getAgenciesImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, agenciesImage);
    }
    @GetMapping(value = "rockets/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.ROCKET_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getRocketImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, rocketsImage);
    }
    @GetMapping(value = "spacecraft/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.SPACECRAFT_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getSpacecraftImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, spacecraftImage);
    }
    @GetMapping(value = "pads/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.PADS_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getPadImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, padsImage);
    }
    @GetMapping(value = "pads/location/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.PADS_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getPadLocationImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, padsImageLocation);
    }
    @GetMapping(value = "astronauts/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.ASTRONAUTS_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getAstronautImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, astronautsImage);
    }
    @GetMapping(value = "launchers/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.LAUNCHERS_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getLauncherImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, launchersImage);
    }
    @GetMapping(value = "mission-patches/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.MISSION_PATCHES_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getMissionPatchesImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, missionPatchesImage);
    }
    @GetMapping(value = "programs/{imagePath}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Cacheable(value = CacheNames.PROGRAMS_IMAGES_CACHE,  key = "'image-' + #imagePath", sync = true)
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Resource> getProgramImages(@PathVariable String imagePath) {
        return getResourceResponseEntity(imagePath, programImages);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(@PathVariable String imagePath, String launchersImage) {
        Path fullImagePath = Paths.get(launchersImage, imagePath);
        LOGGER.info("Local Image Path -> " + fullImagePath);

        if (Files.exists(fullImagePath)) {
            try {
                byte[] imageData = Files.readAllBytes(fullImagePath);
                ByteArrayResource resource = new ByteArrayResource(imageData);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + imagePath + "\"")
                        .contentType(getMediaType(imagePath))
                        .body(resource);

            } catch (IOException e) {
                LOGGER.error("Error reading image file: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private MediaType getMediaType(String imagePath) {
        if (imagePath.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (imagePath.endsWith(".jpg") || imagePath.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
