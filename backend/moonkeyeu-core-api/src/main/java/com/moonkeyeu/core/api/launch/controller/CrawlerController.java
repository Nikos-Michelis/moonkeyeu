package com.moonkeyeu.core.api.launch.controller;
import com.moonkeyeu.core.api.launch.dto.CrawlerDTO;
import com.moonkeyeu.core.api.launch.dto.ImageDTO;
import com.moonkeyeu.core.api.launch.dto.agency.AgencyDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.astronaut.AstronautDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchDTO;
import com.moonkeyeu.core.api.launch.dto.pad.LaunchPadDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.program.ProgramDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.spacecraft.SpacecraftConfigurationDTO;
import com.moonkeyeu.core.api.launch.services.*;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("crawler")
@RequiredArgsConstructor
public class CrawlerController {
    private final LaunchService launchService;
    private final AstronautService astronautService;
    private final ProgramsService programsService;
    private final SpacecraftService spacecraftService;
    private final LaunchPadService launchPadService;
    private final AgenciesService agenciesService;
    private final CrawlerService crawlerService;
    private final String DEFAULT_DESCRIPTION =
            "Stay up to date with upcoming and past spaceflight from NASA, SpaceX, and other leading space agencies around the world.";
    private final String DEFAULT_IMG_URL = "https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg";
    @GetMapping("/default")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getDefaultPreview(
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        return crawlerService.getDefaultPreview(userAgent, "",
                CrawlerDTO.builder()
                        .description(DEFAULT_DESCRIPTION)
                        .image("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg")
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/{segment}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getDefaultPreviewBySegment(
            @PathVariable String segment,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        return crawlerService.getDefaultPreview(userAgent, segment,
                CrawlerDTO.builder()
                        .title(segment.toLowerCase())
                        .description(DEFAULT_DESCRIPTION)
                        .image("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg")
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }

    @GetMapping("/launch/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getLaunchPreview(
            @PathVariable String id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        LaunchDTO launchDTO = (LaunchDTO) launchService.getLaunchById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Launch not found with id: " + id));
        String launchDescription = launchDTO.getRocket().getRocketConfiguration().getDescription();
        return crawlerService.handlePreview(userAgent, id, "launches",
                CrawlerDTO.builder()
                        .title(launchDTO.getLaunchName())
                        .description(
                                launchDescription != null && !launchDescription.isEmpty()
                                        ? launchDescription
                                        : DEFAULT_DESCRIPTION
                        )
                        .image(launchDTO.getRocketConfImages() != null
                                ? launchDTO.getRocketConfImages().getImageUrl()
                                : DEFAULT_IMG_URL)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/astronaut/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getAstronautPreview(
            @PathVariable Integer id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        AstronautDetailedDTO astronautDetailedDTO = (AstronautDetailedDTO) astronautService.getAstronautById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astronaut not found with id: " + id));
        Optional<ImageDTO> imageUrls = astronautDetailedDTO.getAstronautImages().stream().findFirst();
        String imageUrl = imageUrls.map(ImageDTO::getImageUrl).orElse("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg");
        return crawlerService.handlePreview(userAgent, id, "astronauts",
                CrawlerDTO.builder()
                        .title(astronautDetailedDTO.getName())
                        .description(
                                astronautDetailedDTO.getBio() != null && !astronautDetailedDTO.getBio().isEmpty()
                                        ? astronautDetailedDTO.getBio()
                                        : DEFAULT_DESCRIPTION
                        )
                        .image(imageUrl)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/program/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getProgramPreview(
            @PathVariable Integer id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        ProgramDetailedDTO programDetailedDTO = (ProgramDetailedDTO) programsService.getProgramById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
        Optional<ImageDTO> imageUrls = programDetailedDTO.getProgramImages().stream().findFirst();
        String imageUrl = imageUrls.map(ImageDTO::getImageUrl).orElse("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg");
        return crawlerService.handlePreview(userAgent, id, "programs",
                CrawlerDTO.builder()
                        .title(programDetailedDTO.getTypeName())
                        .description(
                                programDetailedDTO.getDescription() != null && !programDetailedDTO.getDescription().isEmpty()
                                        ? programDetailedDTO.getDescription()
                                        : DEFAULT_DESCRIPTION
                        )
                        .image(imageUrl)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/spacecraft/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getSpacecraftPreview(
            @PathVariable Integer id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        SpacecraftConfigurationDTO spacecraftDTO = spacecraftService.getSpacecraftById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spacecraft not found with id: " + id));
        Optional<ImageDTO> imageUrls = spacecraftDTO.getSpacecraftConfImages().stream().findFirst();
        String imageUrl = imageUrls.map(ImageDTO::getImageUrl).orElse("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg");
        return crawlerService.handlePreview(userAgent, id, "vehicles/spacecraft",
                CrawlerDTO.builder()
                        .title(spacecraftDTO.getSpacecraftConfName())
                        .description(
                                spacecraftDTO.getDetails() != null && !spacecraftDTO.getDetails().isEmpty()
                                        ? spacecraftDTO.getDetails()
                                        : DEFAULT_DESCRIPTION
                        )
                        .image(imageUrl)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/launch-pad/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getLaunchPadPreview(
            @PathVariable Integer id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        LaunchPadDetailedDTO launchPadDTO = (LaunchPadDetailedDTO) launchPadService.getLaunchPadById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LaunchPad not found with id: " + id));
        String imageUrl = launchPadDTO.getMapImage() != null ? launchPadDTO.getMapImage() : "https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg";
        return crawlerService.handlePreview(userAgent, id, "locations",
                CrawlerDTO.builder()
                        .title(launchPadDTO.getLaunchPadName())
                        .description(
                                launchPadDTO.getDescription() != null && !launchPadDTO.getDescription().isEmpty()
                                    ? launchPadDTO.getDescription()
                                    : launchPadDTO.getLocation().getDescription()
                        )
                        .image(imageUrl)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
    @GetMapping("/agency/{id}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<Object> getAgencyPreview(
            @PathVariable Integer id,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {
        AgencyDetailedDTO agencyDetailedDTO = (AgencyDetailedDTO) agenciesService.getAgencyById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + id));
        Optional<ImageDTO> imageUrls = agencyDetailedDTO.getAgenciesImages().stream().findFirst();
        String imageUrl = imageUrls.map(ImageDTO::getImageUrl).orElse("https://cdn.moonkeyeu.com/media/assets/logo/moonkeyeu-logo.jpg");
        return crawlerService.handlePreview(userAgent, id, "agencies",
                CrawlerDTO.builder()
                        .title(agencyDetailedDTO.getAgencyName())
                        .description(
                                agencyDetailedDTO.getDescription() != null && !agencyDetailedDTO.getDescription().isEmpty()
                                    ? agencyDetailedDTO.getDescription()
                                    : DEFAULT_DESCRIPTION
                        )
                        .image(imageUrl)
                        .datePublished(Instant.now())
                        .dateModified(Instant.now())
                        .build());
    }
}
