package com.moonkeyeu.etl.api.configuration.files;

import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class CsvRawData {
    private final String rawRootFolder;
    public final String JSON_AGENCIES_SOURCE_FILE;
    public final String JSON_SOURCE_FILE;
    public final String RAW_LAUNCHES_CSV;
    public final String RAW_AGENCIES_CSV;
    public final String RAW_BOOSTERS_CSV;
    public final String RAW_SPACECRAFT_STAGE_CSV;
    public final String RAW_ASTRONAUTS_CSV;
    public final String RAW_MISSION_PATCHES_CSV;
    public final String RAW_ASTRONAUTS_COUNTRIES_CSV;
    public final String RAW_AGENCY_COUNTRIES_CSV;
    public final String RAW_MISSIONS_AGENCIES_CSV;
    public final String RAW_PROGRAMS_CSV;
    public final String RAW_VIDEO_CSV;
    public final String RAW_UPDATES_CSV;
    public final String RAW_INFO_URLS_CSV;
    public final String RAW_SOCIAL_MEDIA_CSV;
    public final String LAUNCH_PROVIDERS_COUNTRIES_CSV;
    public final String MANUFACTURER_COUNTRIES_CSV;
    public final String ASTRONAUTS_COUNTRIES_CSV;
    public final String PROGRAMS_AGENCIES_CSV;

    public CsvRawData(RootConfig rootConfig) {
        this.rawRootFolder = rootConfig.getRawRootFolder();
        this.JSON_AGENCIES_SOURCE_FILE = buildPath("all-agencies.json");
        this.JSON_SOURCE_FILE = buildPath("space-missions.json");
        this.RAW_LAUNCHES_CSV = buildPath("space-missions.csv");
        this.RAW_AGENCIES_CSV = buildPath("all-agencies.csv");
        this.RAW_BOOSTERS_CSV = buildPath("rocket-boosters.csv");
        this.RAW_SPACECRAFT_STAGE_CSV = buildPath("spacecraft-stage.csv");
        this.RAW_ASTRONAUTS_CSV = buildPath("astronauts.csv");
        this.RAW_MISSION_PATCHES_CSV = buildPath("mission-patches.csv");
        this.RAW_ASTRONAUTS_COUNTRIES_CSV = buildPath("astronaut-countries.csv");
        this.RAW_AGENCY_COUNTRIES_CSV = buildPath("agency-countries.csv");
        this.RAW_MISSIONS_AGENCIES_CSV = buildPath("mission-agencies.csv");
        this.RAW_PROGRAMS_CSV = buildPath("programs.csv");
        this.RAW_VIDEO_CSV = buildPath("launch-video.csv");
        this.RAW_UPDATES_CSV = buildPath("launch-updates.csv");
        this.RAW_INFO_URLS_CSV = buildPath("info-url.csv");
        this.RAW_SOCIAL_MEDIA_CSV = buildPath("social-media.csv");
        this.LAUNCH_PROVIDERS_COUNTRIES_CSV = buildPath("launch-provider-has-countries.csv");
        this.MANUFACTURER_COUNTRIES_CSV = buildPath("manufacturer-has-countries.csv");
        this.ASTRONAUTS_COUNTRIES_CSV = buildPath("astronaut-has-countries.csv");
        this.PROGRAMS_AGENCIES_CSV = buildPath("programs-has-agencies.csv");
    }

    private String buildPath(String filename) {
        return Paths.get(rawRootFolder, filename).toString();
    }
}
