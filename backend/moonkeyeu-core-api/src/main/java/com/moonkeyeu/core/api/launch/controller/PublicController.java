package com.moonkeyeu.core.api.launch.controller;

import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.dto.NasaApodDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.security.limiter.RateLimited;
import com.moonkeyeu.core.api.launch.services.*;
import com.moonkeyeu.core.api.user.dto.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final SpacecraftService spacecraftService;
    private final ProgramsService programsService;
    private final AgenciesService agenciesService;
    private final LaunchPadService launchPadService;
    private final LaunchService launchService;
    private final AstronautService astronautService;
    private final RocketService rocketService;
    private final LauncherService launcherService;
    private final FiltersService filtersService;
    private final NasaApodService nasaApodService;
    private final int MAX_ITEMS = 50;

    @GetMapping("/nasa/apod")
    @RateLimited(requests = 20, durationSeconds = 60)
    public ResponseEntity<?> getNasaPictureOfTheDay() {
        return ResponseEntity.ok(nasaApodService.getNasaApodFromCache());
    }
    @GetMapping("/launch/{launchId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getLaunchById(@PathVariable String launchId) {
        DTOEntity dtoEntity = launchService.getLaunchById(launchId)
                .orElseThrow(() -> new ResourceNotFoundException("Launch not found with id: " + launchId));
        return ResponseEntity.ok(dtoEntity);
    }
    @GetMapping("/launches")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllLaunches(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "net") String field,
            @RequestParam(defaultValue = "asc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(launchService.searchLaunch(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }

    @GetMapping("/astronauts")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllAstronauts(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(astronautService.searchAstronaut(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }

    @GetMapping("/astronaut/{astronautId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getAstronautById(@PathVariable Integer astronautId) {
        DTOEntity dtoEntity = astronautService.getAstronautById(astronautId)
                .orElseThrow(() -> new ResourceNotFoundException("Astronaut not found with id: " + astronautId));
        return ResponseEntity.ok(dtoEntity);
    }
    @GetMapping("/rockets")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllRockets(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "maidenFlight") String field,
            @RequestParam(defaultValue = "desc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(rocketService.searchRocket(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }
    @GetMapping("/rocket/{rocketId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getRocketById(@PathVariable Integer rocketId) {
        DTOEntity dtoEntity = rocketService.getRocketById(rocketId)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket not found with id: " + rocketId));
        return ResponseEntity.ok(dtoEntity);
    }

    @GetMapping("/spacecraft/{spacecraftId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getSpacecraftStageById(@PathVariable Integer spacecraftId) {
        DTOEntity dtoEntity =  spacecraftService.getSpacecraftById(spacecraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Spacecraft not found with id: " + spacecraftId));
        return ResponseEntity.ok(dtoEntity);
    }

    @GetMapping("/spacecraft")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllSpacecraft(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "maidenFlight") String field,
            @RequestParam(defaultValue = "asc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(spacecraftService.searchSpacecraft(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }
    @GetMapping("/programs")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllPrograms(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "startDate") String field,
            @RequestParam(defaultValue = "desc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(programsService.searchProgram(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }
    @GetMapping("/program/{programId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getProgramById(@PathVariable Integer programId) {
        DTOEntity dtoEntity =  programsService.getProgramById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + programId));
        return ResponseEntity.ok(dtoEntity);
    }
    @GetMapping("/agencies")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getAllAgencies() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .data(agenciesService.getAllAgencies())
                        .build()
               );
    }
    @GetMapping("/agency/{agencyId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getAgencyById(@PathVariable Integer agencyId) {
        DTOEntity dtoEntity =  agenciesService.getAgencyById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found with id: " + agencyId));
        return ResponseEntity.ok(dtoEntity);
    }
    @GetMapping("/launch-pads")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<?> getAllLaunchPads() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .timestamp(Instant.now())
                        .data(launchPadService.getAllLaunchPads())
                        .build());
    }

    @GetMapping("/launch-pad/{launchPadId}")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<DTOEntity> getLaunchPadById(@PathVariable Integer launchPadId) {
        DTOEntity dtoEntity = launchPadService.getLaunchPadById(launchPadId)
                .orElseThrow(() -> new ResourceNotFoundException("LaunchPad not found with id: " + launchPadId));
        return ResponseEntity.ok(dtoEntity);
    }

    @GetMapping("/launchers")
    @RateLimited(requests = 100, durationSeconds = 60)
    public ResponseEntity<PagedModel<EntityModel<DTOEntity>>> getAllLaunchers(
            @RequestParam(required = false) Map<String, String> requestParams,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "serialNumber") String field,
            @RequestParam(defaultValue = "asc") String ordering,
            PagedResourcesAssembler<DTOEntity> assembler) {
        return ResponseEntity
                .ok(assembler.toModel(launcherService.searchLauncher(requestParams,
                        PageSortingDTO.builder()
                                .page(page)
                                .limit(limit >= MAX_ITEMS ? MAX_ITEMS : limit)
                                .field(field)
                                .sort(ordering)
                                .build())));
    }

    @GetMapping("/launches/filters")
    @RateLimited(requests = 100, durationSeconds = 60)
    public Map<String, Object> getLaunchFilters() {
        return filtersService.getLaunchFilters();
    }
    @GetMapping("/astronauts/filters")
    @RateLimited(requests = 100, durationSeconds = 60)
    public Map<String, Object> getAstronautFilters() {
        return filtersService.getAstronautFilters();
    }

}
