package com.moonkeyeu.etl.api.configuration.files;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
@Data
public class ExtractImages {
 private final String rawRootFolder;
 private final String rootPathImages;
 public final String RAW_AGENCIES_IMAGES_CSV;
 public final String RAW_ASTRONAUT_IMAGES_CSV;
 public final String RAW_LAUNCHER_IMAGES_CSV;
 public final String RAW_PAD_IMAGES_CSV;
 public final String RAW_ROCKET_CONFIGURATION_IMAGES_CSV;
 public final String RAW_LAUNCH_ROCKET_IMAGES_CSV;
 public final String RAW_SPACECRAFT_IMAGES_CSV;
 public final String RAW_PROGRAMS_IMAGES_CSV;
 public final String DOWNLOAD_ROCKET_DIR;
 public final String DOWNLOAD_LAUNCH_ROCKET_DIR;
 public final String DOWNLOAD_SPACECRAFT_DIR;
 public final String DOWNLOAD_ASTRONAUTS_DIR;
 public final String DOWNLOAD_AGENCIES_DIR;
 public final String DOWNLOAD_LAUNCHERS_DIR;
 public final String DOWNLOAD_MISSIONS_PATCHES_DIR;
 public final String DOWNLOAD_PADS_DIR;
 public final String DOWNLOAD_PADS_LOCATIONS_DIR;
 public final String DOWNLOAD_PROGRAMS_DIR;

 public ExtractImages(RootConfig rootConfig) {
  this.rawRootFolder = rootConfig.getRawRootFolder();
  this.rootPathImages = rootConfig.getImagesRootFolder();
  this.RAW_AGENCIES_IMAGES_CSV = buildPath(rawRootFolder, "agencies-images.csv");
  this.RAW_ASTRONAUT_IMAGES_CSV = buildPath(rawRootFolder, "astronaut-images.csv");
  this.RAW_LAUNCHER_IMAGES_CSV = buildPath(rawRootFolder, "launcher-images.csv");
  this.RAW_PAD_IMAGES_CSV = buildPath(rawRootFolder, "pad-images.csv");
  this.RAW_LAUNCH_ROCKET_IMAGES_CSV = buildPath(rawRootFolder, "launch-rocket-images.csv");
  this.RAW_ROCKET_CONFIGURATION_IMAGES_CSV = buildPath(rawRootFolder, "rocket-config-images.csv");
  this.RAW_SPACECRAFT_IMAGES_CSV = buildPath(rawRootFolder, "spacecraft-images.csv");
  this.RAW_PROGRAMS_IMAGES_CSV = buildPath(rawRootFolder, "programs-images.csv");

  this.DOWNLOAD_LAUNCH_ROCKET_DIR = buildDownloadDir("rockets");
  this.DOWNLOAD_ROCKET_DIR = buildDownloadDir("launch");
  this.DOWNLOAD_SPACECRAFT_DIR = buildDownloadDir("spacecraft");
  this.DOWNLOAD_ASTRONAUTS_DIR = buildDownloadDir("astronauts");
  this.DOWNLOAD_AGENCIES_DIR = buildDownloadDir("agencies");
  this.DOWNLOAD_LAUNCHERS_DIR = buildDownloadDir("launchers");
  this.DOWNLOAD_MISSIONS_PATCHES_DIR = buildDownloadDir("missions-patches");
  this.DOWNLOAD_PADS_DIR = buildDownloadDir("pads");
  this.DOWNLOAD_PADS_LOCATIONS_DIR = buildDownloadDir("pads-locations");
  this.DOWNLOAD_PROGRAMS_DIR = buildDownloadDir("programs");
 }

 public String buildPath(String rootFolder, String filename) {
  return Paths.get(rootFolder, filename).toString();
 }
 private String buildDownloadDir(String dirName) {
  return Paths.get(rootPathImages, dirName).toString();
 }
}
