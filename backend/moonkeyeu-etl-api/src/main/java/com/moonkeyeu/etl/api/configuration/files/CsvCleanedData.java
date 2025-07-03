package com.moonkeyeu.etl.api.configuration.files;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class CsvCleanedData {
 private final String cleanRootFolder;
 public final String ROCKET_CSV;
 public final String ROCKET_CONFIGURATION_CSV;
 public final String LAUNCH_PAD_CSV;
 public final String LOCATIONS_CSV;
 public final String LAUNCH_MISSION_CSV;
 public final String LAUNCH_ORBIT_CSV;
 public final String AGENCIES_CSV;
 public final String AGENCIES_TYPES_CSV;
 public final String LAUNCH_CSV;
 public final String LAUNCH_STATUS_CSV;
 public final String LANDINGS_CSV;
 public final String LANDING_TYPE_CSV;
 public final String LANDING_ZONE_CSV;
 public final String LAUNCHER_STAGE_CSV;
 public final String LAUNCHER_CSV;
 public final String ASTRONAUTS_CSV;
 public final String ASTRONAUT_STATUS_CSV;
 public final String ASTRONAUTS_ROLES_CSV;
 public final String CREW_MEMBER_CSV;
 public final String SPACECRAFT_CSV;
 public final String SPACECRAFT_CONFIG_CSV;
 public final String SPACECRAFT_STAGE_CSV;
 public final String SPACECRAFT_TYPE_CSV;
 public final String MISSION_PATCHES_CSV;
 public final String LAUNCHER_STATUS_CSV;
 public final String SPACECRAFT_STATUS_CSV;
 public final String SOCIAL_MEDIA_CSV;
 public final String VIDEO_CSV;
 public final String UPDATES_CSV;
 public final String INFO_URLS_CSV;
 public final String COUNTRIES_CSV;
 public final String PROGRAMS_TYPES_CSV;
 public final String PROGRAMS_CSV;
 public final String MISSION_HAS_AGENCIES_CSV;
 public final String ASTRONAUT_HAS_COUNTRIES_CSV;
 public final String AGENCIES_HAS_COUNTRIES_CSV;
 public final String PROGRAMS_HAS_AGENCIES_CSV;
 public final String LAUNCH_HAS_PROGRAMS_CSV;
 public final String LAUNCH_PAD_HAS_AGENCIES_CSV;
 public final String AGENCIES_IMAGES_CSV;
 public final String ASTRONAUT_IMAGES_CSV;
 public final String LAUNCHER_IMAGES_CSV;
 public final String PAD_IMAGES_CSV;
 public final String ROCKET_CONFIGURATION_IMAGES_CSV;
 public final String LAUNCH_ROCKET_IMAGES_CSV;
 public final String SPACECRAFT_IMAGES_CSV;
 public final String PROGRAMS_IMAGES_CSV;

 public CsvCleanedData(RootConfig rootConfig) {
  this.cleanRootFolder = rootConfig.getCleanRootFolder();

  this.ROCKET_CSV = buildPath("rocket.csv");
  this.ROCKET_CONFIGURATION_CSV = buildPath("rocket-configuration.csv");
  this.LAUNCH_PAD_CSV = buildPath("launch-pad.csv");
  this.LOCATIONS_CSV = buildPath("locations.csv");
  this.LAUNCH_MISSION_CSV = buildPath("mission.csv");
  this.LAUNCH_ORBIT_CSV = buildPath("orbit.csv");
  this.AGENCIES_CSV = buildPath("agencies.csv");
  this.AGENCIES_TYPES_CSV = buildPath("agencies-types.csv");
  this.LAUNCH_CSV = buildPath("launch.csv");
  this.LAUNCH_STATUS_CSV = buildPath("launch-status.csv");
  this.LANDINGS_CSV = buildPath("landing.csv");
  this.LANDING_TYPE_CSV = buildPath("landing-type.csv");
  this.LANDING_ZONE_CSV = buildPath("landing-zone.csv");
  this.LAUNCHER_STAGE_CSV = buildPath("launcher-stage.csv");
  this.LAUNCHER_CSV = buildPath("launcher.csv");
  this.ASTRONAUTS_CSV = buildPath("astronaut.csv");
  this.ASTRONAUT_STATUS_CSV = buildPath("astronaut-status.csv");
  this.ASTRONAUTS_ROLES_CSV = buildPath("astronaut-roles.csv");
  this.CREW_MEMBER_CSV = buildPath("crew-member.csv");
  this.SPACECRAFT_CSV = buildPath("spacecraft.csv");
  this.SPACECRAFT_CONFIG_CSV = buildPath("spacecraft-configuration.csv");
  this.SPACECRAFT_STAGE_CSV = buildPath("spacecraft-stage.csv");
  this.SPACECRAFT_TYPE_CSV = buildPath("spacecraft-type.csv");
  this.MISSION_PATCHES_CSV = buildPath("missions-patches.csv");
  this.LAUNCHER_STATUS_CSV = buildPath("launcher-status.csv");
  this.SPACECRAFT_STATUS_CSV = buildPath("spacecraft-status.csv");
  this.SOCIAL_MEDIA_CSV = buildPath("social-media.csv");
  this.VIDEO_CSV = buildPath("video.csv");
  this.UPDATES_CSV = buildPath("updates.csv");
  this.INFO_URLS_CSV = buildPath("info-url.csv");
  this.COUNTRIES_CSV = buildPath("all-countries.csv");
  this.PROGRAMS_TYPES_CSV = buildPath("programs-types.csv");
  this.PROGRAMS_CSV = buildPath("programs.csv");
  this.MISSION_HAS_AGENCIES_CSV = buildPath("mission-has-agencies.csv");
  this.ASTRONAUT_HAS_COUNTRIES_CSV = buildPath("astronaut-has-countries.csv");
  this.AGENCIES_HAS_COUNTRIES_CSV = buildPath("agencies-has-countries.csv");
  this.PROGRAMS_HAS_AGENCIES_CSV = buildPath("programs-has-agencies.csv");
  this.LAUNCH_HAS_PROGRAMS_CSV = buildPath("launch-has-programs.csv");
  this.LAUNCH_PAD_HAS_AGENCIES_CSV = buildPath("launch-pad-has-agencies.csv");
  this.AGENCIES_IMAGES_CSV = buildPath("agencies-images.csv");
  this.ASTRONAUT_IMAGES_CSV = buildPath("astronaut-images.csv");
  this.LAUNCHER_IMAGES_CSV = buildPath("launcher-images.csv");
  this.PAD_IMAGES_CSV = buildPath("pad-images.csv");
  this.ROCKET_CONFIGURATION_IMAGES_CSV = buildPath("rocket-config-images.csv");
  //new
  this.LAUNCH_ROCKET_IMAGES_CSV = buildPath("launch-rocket-images.csv");
  this.SPACECRAFT_IMAGES_CSV = buildPath("spacecraft-images.csv");
  this.PROGRAMS_IMAGES_CSV = buildPath("programs-images.csv");
 }

 private String buildPath(String fileName) {
  return Paths.get(cleanRootFolder, fileName).toString();
 }
}
