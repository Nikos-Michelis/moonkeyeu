package com.moonkeyeu.etl.api.service.impl;

import com.moonkeyeu.etl.api.configuration.files.CsvCleanedData;
import com.moonkeyeu.etl.api.configuration.files.CsvRawData;
import com.moonkeyeu.etl.api.configuration.files.ExtractImages;
import com.moonkeyeu.etl.api.configuration.s3.S3Buckets;
import com.moonkeyeu.etl.api.dto.clean.CsvEntity;
import com.moonkeyeu.etl.api.dto.clean.media.MissionPatches;
import com.moonkeyeu.etl.api.dto.clean.astronauts.Astronaut;
import com.moonkeyeu.etl.api.dto.clean.images.*;
import com.moonkeyeu.etl.api.dto.clean.media.InfoUrl;
import com.moonkeyeu.etl.api.dto.clean.media.SocialMedia;
import com.moonkeyeu.etl.api.dto.clean.pads.LaunchPadsHasAgencies;
import com.moonkeyeu.etl.api.model.LocationEntity;
import com.moonkeyeu.etl.api.model.agency.AgenciesEntity;
import com.moonkeyeu.etl.api.model.agency.AgenciesImagesEntity;
import com.moonkeyeu.etl.api.model.agency.AgencyTypeEntity;
import com.moonkeyeu.etl.api.model.country.AgenciesHasCountry;
import com.moonkeyeu.etl.api.model.country.CountryEntity;
import com.moonkeyeu.etl.api.model.landing.LandingEntity;
import com.moonkeyeu.etl.api.model.landing.LandingTypeEntity;
import com.moonkeyeu.etl.api.model.landing.LandingZoneEntity;
import com.moonkeyeu.etl.api.model.launch.LaunchEntity;
import com.moonkeyeu.etl.api.model.launch.LaunchImagesEntity;
import com.moonkeyeu.etl.api.model.launch.LaunchStatusEntity;
import com.moonkeyeu.etl.api.model.launcher.LauncherEntity;
import com.moonkeyeu.etl.api.model.launcher.LauncherImagesEntity;
import com.moonkeyeu.etl.api.model.launcher.LauncherStageEntity;
import com.moonkeyeu.etl.api.model.launcher.LauncherStatusEntity;
import com.moonkeyeu.etl.api.model.pad.LaunchPadEntity;
import com.moonkeyeu.etl.api.model.pad.LaunchPadHasAgenciesEntity;
import com.moonkeyeu.etl.api.model.pad.LaunchPadImagesEntity;
import com.moonkeyeu.etl.api.model.mission.MissionEntity;
import com.moonkeyeu.etl.api.model.mission.MissionHasAgenciesEntity;
import com.moonkeyeu.etl.api.model.mission.OrbitEntity;
import com.moonkeyeu.etl.api.model.rocket.RocketConfigurationEntity;
import com.moonkeyeu.etl.api.model.rocket.RocketConfigurationImagesEntity;
import com.moonkeyeu.etl.api.model.rocket.RocketEntity;
import com.moonkeyeu.etl.api.repository.GenericRepositoryFactoryImpl;
import com.moonkeyeu.etl.api.service.CsvService;
import com.moonkeyeu.etl.api.service.DataManagerService;
import com.moonkeyeu.etl.api.service.LocalStorageService;
import com.moonkeyeu.etl.api.service.S3StorageService;
import com.moonkeyeu.etl.api.utils.data.DataCleanerHandler;
import com.moonkeyeu.etl.api.dto.clean.Location;
import com.moonkeyeu.etl.api.dto.clean.agency.AgenciesTypes;
import com.moonkeyeu.etl.api.dto.clean.agency.Agency;
import com.moonkeyeu.etl.api.dto.clean.astronauts.AstronautStatus;
import com.moonkeyeu.etl.api.dto.clean.astronauts.CrewMember;
import com.moonkeyeu.etl.api.dto.clean.astronauts.Roles;
import com.moonkeyeu.etl.api.dto.clean.country.AgencyHasCountry;
import com.moonkeyeu.etl.api.dto.clean.country.AstronautHasCountry;
import com.moonkeyeu.etl.api.dto.clean.country.Country;
import com.moonkeyeu.etl.api.dto.clean.media.Updates;
import com.moonkeyeu.etl.api.dto.clean.media.Video;
import com.moonkeyeu.etl.api.dto.clean.landing.Landing;
import com.moonkeyeu.etl.api.dto.clean.landing.LandingType;
import com.moonkeyeu.etl.api.dto.clean.landing.LandingZone;
import com.moonkeyeu.etl.api.dto.clean.launch.Launch;
import com.moonkeyeu.etl.api.dto.clean.pads.LaunchPad;
import com.moonkeyeu.etl.api.dto.clean.launch.LaunchStatus;
import com.moonkeyeu.etl.api.dto.clean.launcher.Launcher;
import com.moonkeyeu.etl.api.dto.clean.launcher.LauncherStage;
import com.moonkeyeu.etl.api.dto.clean.launcher.LauncherStatus;
import com.moonkeyeu.etl.api.dto.clean.mission.Mission;
import com.moonkeyeu.etl.api.dto.clean.mission.MissionsHasAgencies;
import com.moonkeyeu.etl.api.dto.clean.mission.Orbit;
import com.moonkeyeu.etl.api.dto.clean.program.LaunchHasPrograms;
import com.moonkeyeu.etl.api.dto.clean.program.Program;
import com.moonkeyeu.etl.api.dto.clean.program.ProgramHasAgencies;
import com.moonkeyeu.etl.api.dto.clean.program.ProgramType;
import com.moonkeyeu.etl.api.dto.clean.rocket.Rocket;
import com.moonkeyeu.etl.api.dto.clean.rocket.RocketConfiguration;
import com.moonkeyeu.etl.api.dto.clean.spacecraft.*;
import com.moonkeyeu.etl.api.model.crew.*;
import com.moonkeyeu.etl.api.model.media.*;
import com.moonkeyeu.etl.api.model.programs.*;
import com.moonkeyeu.etl.api.model.spacecraft.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DataManagerServiceImpl implements DataManagerService {
    private final CsvService csvService;
    private final LocalStorageService localStorageService;
    private final S3StorageService s3StorageService;
    private final S3Buckets s3Buckets;
    private final EntityManager entityManager;
    private final DataCleanerHandler dataCleanerHandler;
    private final CsvCleanedData csvCleanedData;
    private final CsvRawData csvRawData;
    private final ExtractImages extractImages;

    @Autowired
    public DataManagerServiceImpl(
            CsvService csvService,
            LocalStorageService localStorageService,
            S3StorageService s3StorageService,
            S3Buckets s3Buckets,
            EntityManager entityManager,
            DataCleanerHandler dataCleanerHandler,
            CsvCleanedData csvCleanedData,
            CsvRawData csvRawData,
            ExtractImages extractImages
    ){
        this.csvService = csvService;
        this.localStorageService = localStorageService;
        this.s3StorageService = s3StorageService;
        this.s3Buckets = s3Buckets;
        this.entityManager = entityManager;
        this.dataCleanerHandler = dataCleanerHandler;
        this.csvCleanedData = csvCleanedData;
        this.csvRawData = csvRawData;
        this.extractImages = extractImages;
    }
    @Override
    public void processData(String inputFile, String outputFile, Class<? extends CsvEntity> type,
                            boolean isS3Enable, boolean skipUpload) throws IOException {

        List<CsvEntity> csvEntities = csvService.loadCSV(inputFile, type);
        List<CsvEntity> uniqueEntities = dataCleanerHandler.getUniqueEntities(csvEntities);
        String[] headers = csvService.getHeaders(type);
        if (!isS3Enable) {
            localStorageService.saveMediaLocal(uniqueEntities, outputFile, headers, type);
        }
        if(isS3Enable) {
            s3StorageService.saveMediaToS3(uniqueEntities, s3Buckets.getDbImages(), outputFile, headers, type, skipUpload);
        }
        csvEntities.clear();
    }

    public void cleanRawData(boolean skipUpload) throws IOException {
        String[] rawFileNames = {
                csvRawData.RAW_AGENCY_COUNTRIES_CSV, csvRawData.RAW_AGENCIES_CSV, csvRawData.RAW_AGENCIES_CSV, extractImages.RAW_AGENCIES_IMAGES_CSV, csvRawData.RAW_LAUNCHES_CSV,
                csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV,
                csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_BOOSTERS_CSV,
                csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_BOOSTERS_CSV,
                csvRawData.RAW_ASTRONAUTS_CSV, csvRawData.RAW_ASTRONAUTS_CSV, csvRawData.RAW_ASTRONAUTS_CSV, csvRawData.RAW_ASTRONAUTS_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV,
                csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV,
                csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_MISSION_PATCHES_CSV, csvRawData.RAW_VIDEO_CSV,
                csvRawData.RAW_UPDATES_CSV, csvRawData.RAW_INFO_URLS_CSV, csvRawData.RAW_SOCIAL_MEDIA_CSV, csvRawData.RAW_ASTRONAUTS_COUNTRIES_CSV, csvRawData.RAW_PROGRAMS_CSV,
                csvRawData.RAW_PROGRAMS_CSV, csvRawData.RAW_ASTRONAUTS_CSV, csvRawData.RAW_BOOSTERS_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_LAUNCHES_CSV,
                csvRawData.RAW_PROGRAMS_CSV, csvRawData.RAW_LAUNCHES_CSV, csvRawData.RAW_SPACECRAFT_STAGE_CSV, csvRawData.RAW_MISSIONS_AGENCIES_CSV, csvRawData.LAUNCH_PROVIDERS_COUNTRIES_CSV,
                csvRawData.MANUFACTURER_COUNTRIES_CSV, csvRawData.ASTRONAUTS_COUNTRIES_CSV, csvRawData.PROGRAMS_AGENCIES_CSV, csvRawData.RAW_PROGRAMS_CSV
        };

        String[] processedFileNames = {
                csvCleanedData.COUNTRIES_CSV, csvCleanedData.AGENCIES_TYPES_CSV, csvCleanedData.AGENCIES_CSV, csvCleanedData.AGENCIES_IMAGES_CSV, csvCleanedData.ROCKET_CSV,
                csvCleanedData.ROCKET_CONFIGURATION_CSV, csvCleanedData.LAUNCH_PAD_CSV, csvCleanedData.LAUNCH_PAD_HAS_AGENCIES_CSV, csvCleanedData.LOCATIONS_CSV, csvCleanedData.LAUNCH_MISSION_CSV,
                csvCleanedData.LAUNCH_ORBIT_CSV, csvCleanedData.LAUNCH_CSV, csvCleanedData.LAUNCH_STATUS_CSV, csvCleanedData.LANDINGS_CSV, csvCleanedData.LANDING_TYPE_CSV,
                csvCleanedData.LANDING_ZONE_CSV, csvCleanedData.LOCATIONS_CSV, csvCleanedData.LAUNCHER_STAGE_CSV, csvCleanedData.LAUNCHER_CSV, csvCleanedData.LAUNCHER_STATUS_CSV,
                csvCleanedData.ASTRONAUTS_CSV, csvCleanedData.ASTRONAUT_STATUS_CSV, csvCleanedData.ASTRONAUTS_ROLES_CSV, csvCleanedData.CREW_MEMBER_CSV, csvCleanedData.SPACECRAFT_STATUS_CSV,
                csvCleanedData.SPACECRAFT_CSV, csvCleanedData.SPACECRAFT_CONFIG_CSV, csvCleanedData.SPACECRAFT_STAGE_CSV, csvCleanedData.LANDINGS_CSV, csvCleanedData.LANDING_TYPE_CSV,
                csvCleanedData.LANDING_ZONE_CSV, csvCleanedData.LOCATIONS_CSV, csvCleanedData.SPACECRAFT_TYPE_CSV, csvCleanedData.MISSION_PATCHES_CSV, csvCleanedData.VIDEO_CSV,
                csvCleanedData.UPDATES_CSV, csvCleanedData.INFO_URLS_CSV, csvCleanedData.SOCIAL_MEDIA_CSV, csvCleanedData.COUNTRIES_CSV, csvCleanedData.PROGRAMS_TYPES_CSV,
                csvCleanedData.PROGRAMS_CSV, csvCleanedData.ASTRONAUT_IMAGES_CSV, csvCleanedData.LAUNCHER_IMAGES_CSV, csvCleanedData.PAD_IMAGES_CSV, csvCleanedData.ROCKET_CONFIGURATION_IMAGES_CSV,
                csvCleanedData.PROGRAMS_IMAGES_CSV, csvCleanedData.LAUNCH_ROCKET_IMAGES_CSV, csvCleanedData.SPACECRAFT_IMAGES_CSV, csvCleanedData.MISSION_HAS_AGENCIES_CSV, csvCleanedData.AGENCIES_HAS_COUNTRIES_CSV,
                csvCleanedData.AGENCIES_HAS_COUNTRIES_CSV, csvCleanedData.ASTRONAUT_HAS_COUNTRIES_CSV, csvCleanedData.PROGRAMS_HAS_AGENCIES_CSV, csvCleanedData.LAUNCH_HAS_PROGRAMS_CSV
        };

        Class<?>[] entityClasses = {
                Country.class, AgenciesTypes.class, Agency.class, AgenciesImages.class, Rocket.class,
                RocketConfiguration.class, LaunchPad.class, LaunchPadsHasAgencies.class, Location.class, Mission.class,
                Orbit.class, Launch.class, LaunchStatus.class, Landing.class, LandingType.class,
                LandingZone.class, Location.class, LauncherStage.class, Launcher.class, LauncherStatus.class,
                Astronaut.class, AstronautStatus.class, Roles.class, CrewMember.class, SpacecraftStatus.class,
                Spacecraft.class, SpaceCraftConfiguration.class, SpaceCraftStage.class, Landing.class, LandingType.class,
                LandingZone.class, Location.class, SpaceCraftType.class, MissionPatches.class, Video.class,
                Updates.class, InfoUrl.class, SocialMedia.class, Country.class, ProgramType.class,
                Program.class, AstronautImages.class, LauncherImages.class, PadImages.class, RocketImages.class,
                ProgramsImages.class, LaunchRocketImages.class, SpacecraftImages.class, MissionsHasAgencies.class, AgencyHasCountry.class,
                AgencyHasCountry.class, AstronautHasCountry.class, ProgramHasAgencies.class, LaunchHasPrograms.class
        };

        for (int i = 0; i < rawFileNames.length; i++) {
            processData(rawFileNames[i], processedFileNames[i], (Class<? extends CsvEntity>) entityClasses[i], true, skipUpload);
        }
    }

    @Transactional
    public void importData() throws IOException, IllegalAccessException {
        String[] fileNames = {
                csvCleanedData.ROCKET_CONFIGURATION_IMAGES_CSV,
                csvCleanedData.AGENCIES_TYPES_CSV, csvCleanedData.AGENCIES_CSV, csvCleanedData.ROCKET_CONFIGURATION_CSV, csvCleanedData.ROCKET_CSV, csvCleanedData.LANDING_TYPE_CSV,
                csvCleanedData.LOCATIONS_CSV, csvCleanedData.LANDING_ZONE_CSV, csvCleanedData.LANDINGS_CSV, csvCleanedData.SPACECRAFT_TYPE_CSV, csvCleanedData.SPACECRAFT_STATUS_CSV,
                csvCleanedData.SPACECRAFT_CONFIG_CSV, csvCleanedData.SPACECRAFT_CSV, csvCleanedData.SPACECRAFT_STAGE_CSV, csvCleanedData.LAUNCHER_STATUS_CSV, csvCleanedData.LAUNCHER_CSV,
                csvCleanedData.LAUNCHER_STAGE_CSV, csvCleanedData.ASTRONAUT_STATUS_CSV, csvCleanedData.ASTRONAUTS_CSV, csvCleanedData.ASTRONAUTS_ROLES_CSV, csvCleanedData.LAUNCH_PAD_CSV,
                csvCleanedData.LAUNCH_PAD_HAS_AGENCIES_CSV, csvCleanedData.LAUNCH_ORBIT_CSV, csvCleanedData.LAUNCH_MISSION_CSV, csvCleanedData.LAUNCH_STATUS_CSV, csvCleanedData.LAUNCH_CSV,
                csvCleanedData.CREW_MEMBER_CSV,csvCleanedData.MISSION_PATCHES_CSV, csvCleanedData.VIDEO_CSV, csvCleanedData.SOCIAL_MEDIA_CSV, csvCleanedData.UPDATES_CSV,
                csvCleanedData.INFO_URLS_CSV, csvCleanedData.COUNTRIES_CSV, csvCleanedData.PROGRAMS_TYPES_CSV, csvCleanedData.PROGRAMS_CSV, csvCleanedData.PROGRAMS_HAS_AGENCIES_CSV,
                csvCleanedData.LAUNCH_HAS_PROGRAMS_CSV, csvCleanedData.PROGRAMS_IMAGES_CSV, csvCleanedData.ASTRONAUT_HAS_COUNTRIES_CSV, csvCleanedData.AGENCIES_HAS_COUNTRIES_CSV, csvCleanedData.MISSION_HAS_AGENCIES_CSV,
                csvCleanedData.AGENCIES_IMAGES_CSV, csvCleanedData.ASTRONAUT_IMAGES_CSV, csvCleanedData.LAUNCHER_IMAGES_CSV, csvCleanedData.PAD_IMAGES_CSV, csvCleanedData.SPACECRAFT_IMAGES_CSV
        };

        Class<?>[] entityClasses = {
                RocketConfigurationImagesEntity.class,
                AgencyTypeEntity.class, AgenciesEntity.class, RocketConfigurationEntity.class, RocketEntity.class, LandingTypeEntity.class,
                LocationEntity.class, LandingZoneEntity.class, LandingEntity.class, SpacecraftTypeEntity.class, SpacecraftStatusEntity.class,
                SpacecraftConfigurationEntity.class, SpacecraftEntity.class, SpacecraftStageEntity.class, LauncherStatusEntity.class, LauncherEntity.class,
                LauncherStageEntity.class, AstronautStatusEntity.class, AstronautEntity.class, RoleEntity.class, LaunchPadEntity.class,
                LaunchPadHasAgenciesEntity.class ,OrbitEntity.class, MissionEntity.class, LaunchStatusEntity.class, LaunchEntity.class,
                CrewMemberEntity.class, MissionPatchesEntity.class, VideoEntity.class, SocialMediaEntity.class, UpdatesEntity.class,
                InfoUrlsEntity.class, CountryEntity.class, ProgramTypeEntity.class, ProgramsEntity.class, ProgramsHasAgenciesEntity.class,
                LaunchHasProgramsEntity.class, ProgramsImagesEntity.class, com.moonkeyeu.etl.api.model.country.AstronautHasCountry.class, AgenciesHasCountry.class, MissionHasAgenciesEntity.class,
                AgenciesImagesEntity.class, AstronautImagesEntity.class, LauncherImagesEntity.class, LaunchPadImagesEntity.class, SpacecraftImagesEntity.class
        };

        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            Class<?> entityClass = entityClasses[i];
            File file = new File(fileName);
            if (file.exists()) {
                loadAndSave(fileName, entityClass);
            } else {
                log.error("File not found: " + fileName);
            }
        }
    }
    public <T> void loadAndSave(String csvFilePath, Class<T> entityType) throws IOException, IllegalAccessException {
        GenericRepositoryFactoryImpl<T, Long> repositoryFactory = new GenericRepositoryFactoryImpl<>(entityManager, entityType);
        List<T> entities = (List<T>) csvService.loadCSV(csvFilePath, (Class<? extends CsvEntity>) entityType);
        for (T entity : entities) {
            dataCleanerHandler.nullIfEmpty(entity);
        }
        repositoryFactory.saveAll(entities);
        entities.clear();
    }
}
