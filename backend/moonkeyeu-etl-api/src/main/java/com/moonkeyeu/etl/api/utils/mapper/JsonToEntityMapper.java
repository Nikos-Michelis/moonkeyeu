package com.moonkeyeu.etl.api.utils.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonkeyeu.etl.api.configuration.files.CsvRawData;
import com.moonkeyeu.etl.api.configuration.files.ExtractImages;
import com.moonkeyeu.etl.api.dto.json.Images.Image;
import com.moonkeyeu.etl.api.dto.json.launch.Launch;
import com.moonkeyeu.etl.api.dto.json.media.MissionPatches;
import com.moonkeyeu.etl.api.dto.json.agency.Agency;
import com.moonkeyeu.etl.api.dto.json.country.AgencyHasCountry;
import com.moonkeyeu.etl.api.dto.json.country.AstronautHasNationalities;
import com.moonkeyeu.etl.api.dto.json.country.Country;
import com.moonkeyeu.etl.api.dto.json.crew.LaunchCrew;
import com.moonkeyeu.etl.api.dto.json.media.InfoUrl;
import com.moonkeyeu.etl.api.dto.json.media.SocialMedia;
import com.moonkeyeu.etl.api.dto.json.media.Updates;
import com.moonkeyeu.etl.api.dto.json.media.Video;
import com.moonkeyeu.etl.api.dto.json.launcher.LauncherStage;
import com.moonkeyeu.etl.api.dto.json.program.Program;
import com.moonkeyeu.etl.api.dto.json.program.ProgramHasAgencies;
import com.moonkeyeu.etl.api.dto.json.spacecraft.SpaceCraftStage;
import com.moonkeyeu.etl.api.service.writer.JsonToCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonToEntityMapper {
    private final JsonToCsvService jsonToCsvService;
    private final CsvRawData csvRawData;
    private final ExtractImages extractImages;
    private final int BATCH_SIZE = 500;

    @Autowired
    public JsonToEntityMapper(JsonToCsvService jsonToCsvService, CsvRawData csvRawData, ExtractImages extractImages) {
        this.jsonToCsvService = jsonToCsvService;
        this.csvRawData = csvRawData;
        this.extractImages = extractImages;
    }

    public void processAgenciesResponse(JsonNode rootNode, ObjectMapper objectMapper) throws Exception {
        List<Agency> resultList = new ArrayList<>();
        JsonNode allResultsNode = rootNode.get("all_results");
        if (allResultsNode != null && allResultsNode.isArray()) {
            for (JsonNode pageNode : allResultsNode) {
                JsonNode resultsNode = pageNode.get("results");
                if (resultsNode != null && resultsNode.isArray()) {
                    for (JsonNode resultNode : resultsNode) {
                        Agency agency = objectMapper.treeToValue(resultNode, Agency.class);
                        resultList.add(agency);
                    }
                    saveAgenciesImages(resultsNode, objectMapper);
                    fetchAgenciesCountries(resultsNode, objectMapper);
                }
                if(!resultList.isEmpty()) {
                    jsonToCsvService.writeDataToCSV(resultList, csvRawData.RAW_AGENCIES_CSV, BATCH_SIZE);
                    resultList.clear();
                }
            }
        }
    }

    public void processLaunchResponse(JsonNode rootNode, ObjectMapper objectMapper) throws Exception {
        List<Launch> resultList = new ArrayList<>();
        JsonNode allResultsNode = rootNode.get("all_results");
        if (allResultsNode != null && allResultsNode.isArray()) {
            for (JsonNode pageNode : allResultsNode) {
                JsonNode resultsNode = pageNode.get("results");
                if (resultsNode != null && resultsNode.isArray()) {
                    for (JsonNode resultNode : resultsNode) {
                        Launch launch = objectMapper.treeToValue(resultNode, Launch.class);
                        resultList.add(launch);
                    }
                    saveLatestRocketImages(resultsNode, objectMapper);
                    saveLaunchPadImages(resultsNode, objectMapper);
                    //saveRocketConfigImages(resultsNode, objectMapper);
                    saveSpacecraftConfigImages(resultsNode, objectMapper);
                    saveLauncherImages(resultsNode, objectMapper);
                    fetchLaunchersStage(resultsNode, objectMapper);
                    fetchSpaceCraftStage(resultsNode, objectMapper);
                    fetchVideo(resultsNode, objectMapper);
                    fetchUpdates(resultsNode, objectMapper);
                    fetchPrograms(resultsNode, objectMapper);
                    fetchMissionPatches(resultsNode, objectMapper);
                    fetchInfoUrl(resultsNode, objectMapper);
                    fetchMissionAgencies(resultsNode, objectMapper);
                }
            }
            if (!resultList.isEmpty()) {
                jsonToCsvService.writeDataToCSV(resultList, csvRawData.RAW_LAUNCHES_CSV, BATCH_SIZE);
            }
        }
    }


    public void fetchMissionAgencies(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<Agency> agencyList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode missionAgencyNodes = resultNode.path("mission").get("agencies");
            if (missionAgencyNodes != null && !missionAgencyNodes.isEmpty() && missionAgencyNodes.isArray()) {
                Integer mission_id = resultNode.path("mission").get("id").asInt();
                for (JsonNode missionAgencyNode : missionAgencyNodes) {
                    Agency agency = objectMapper.treeToValue(missionAgencyNode, Agency.class);
                    agency.setMission_id(mission_id);
                    agencyList.add(agency);
                }
            }
        }
        if(!agencyList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(agencyList, csvRawData.RAW_MISSIONS_AGENCIES_CSV, BATCH_SIZE);
            agencyList.clear();
        }
    }
    public void fetchLaunchersStage(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<LauncherStage> boostersList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            String id = resultNode.get("id").asText();
            Integer rocket_id = resultNode.path("rocket").get("id").asInt();
            JsonNode boostersNode = resultNode.path("rocket").get("launcher_stage");
            if (boostersNode != null && !boostersNode.isEmpty() && boostersNode.isArray()) {
                for (JsonNode boosterNode : boostersNode) {
                    LauncherStage boosterInfo = objectMapper.treeToValue(boosterNode, LauncherStage.class);
                    boosterInfo.setLaunch_id(id);
                    boosterInfo.setRocket_id(rocket_id);
                    boostersList.add(boosterInfo);
                }
            }
        }
        if(!boostersList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(boostersList, csvRawData.RAW_BOOSTERS_CSV, BATCH_SIZE);
            boostersList.clear();
        }
    }
    public void fetchSpaceCraftStage(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<SpaceCraftStage> spaceCraftStageList = new ArrayList<>();
        List<LaunchCrew> crewList = new ArrayList<>();
        List<SocialMedia> socialMediaList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            String id = resultNode.get("id").asText();
            Integer rocket_id = resultNode.path("rocket").get("id").asInt();
            JsonNode spaceCraftStageNodes = resultNode.path("rocket").get("spacecraft_stage");
            if (spaceCraftStageNodes != null && !spaceCraftStageNodes.isEmpty()) {
                for (JsonNode spaceCraftStageNode : spaceCraftStageNodes) {
                    Integer spacecraftStageId = spaceCraftStageNode.get("id").asInt();
                    SpaceCraftStage spaceCraftStage = objectMapper.treeToValue(spaceCraftStageNode, SpaceCraftStage.class);
                    spaceCraftStage.setLaunch_id(id);
                    spaceCraftStage.setRocket_id(rocket_id);
                    spaceCraftStageList.add(spaceCraftStage);
                    crewList.addAll(fetchAstronauts(spaceCraftStageNode, objectMapper, spacecraftStageId, id));
                    socialMediaList.addAll(fetchSocialMediaLinks(spaceCraftStageNode, objectMapper));
                }
            }
        }

        if(!spaceCraftStageList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(spaceCraftStageList, csvRawData.RAW_SPACECRAFT_STAGE_CSV, BATCH_SIZE);
            if(!crewList.isEmpty()){
                jsonToCsvService.writeDataToCSV(crewList, csvRawData.RAW_ASTRONAUTS_CSV, BATCH_SIZE);
                crewList.clear();
            }
            if (!socialMediaList.isEmpty()) {
                jsonToCsvService.writeDataToCSV(socialMediaList, csvRawData.RAW_SOCIAL_MEDIA_CSV, BATCH_SIZE);
                socialMediaList.clear();
            }

        }
    }
    public List<LaunchCrew> fetchAstronauts(JsonNode rootNode, ObjectMapper objectMapper, Integer spacecraftStageId, String launchId) throws Exception {
        List<LaunchCrew> crewList = new ArrayList<>();
        JsonNode crewNodes = rootNode.get("launch_crew");
        if(crewNodes != null && !crewNodes.isEmpty() && crewNodes.isArray()) {
            for (JsonNode crewNode : crewNodes) {
                LaunchCrew crew = objectMapper.treeToValue(crewNode, LaunchCrew.class);
                crew.setSpacecraft_stage_id(spacecraftStageId);
                crew.setLaunch_id(launchId);
                crewList.add(crew);
            }
            fetchAstronautsNationalities(crewNodes, objectMapper);
            saveAstronautImages(rootNode, objectMapper);
        }
        return crewList;
    }
    public void fetchAstronautsNationalities(JsonNode crewNodes, ObjectMapper objectMapper) throws JsonProcessingException {
        List<AstronautHasNationalities> nationalities = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        for (JsonNode crewNode : crewNodes) {
            JsonNode nationalityNodes = crewNode.get("astronaut").get("nationality");
            if (nationalityNodes != null && !nationalityNodes.isEmpty() && nationalityNodes.isArray()) {
                Integer id = crewNode.get("astronaut").get("id").asInt();
                for (JsonNode nationalityNode : nationalityNodes) {
                    Country country = objectMapper.treeToValue(nationalityNode, Country.class);
                    AstronautHasNationalities nationality = new AstronautHasNationalities();
                    nationality.setCountry_id(nationalityNode.get("id").asInt());
                    nationality.setAstronaut_id(id);
                    nationalities.add(nationality);
                    countries.add(country);
                }
            }
        }
        if (!countries.isEmpty()) {
            jsonToCsvService.writeDataToCSV(countries, csvRawData.RAW_ASTRONAUTS_COUNTRIES_CSV, BATCH_SIZE);
            countries.clear();
        }
        if (!nationalities.isEmpty()) {
            jsonToCsvService.writeDataToCSV(nationalities, csvRawData.ASTRONAUTS_COUNTRIES_CSV, BATCH_SIZE);
            nationalities.clear();
        }
    }

    public void fetchAgenciesCountries(JsonNode resultsNode, ObjectMapper objectMapper) throws JsonProcessingException {
        List<AgencyHasCountry> countriesList = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode countriesNode = resultNode.get("country");
            if (countriesNode != null && !countriesNode.isEmpty() && countriesNode.isArray()) {
                Integer agencyId = resultNode.get("id").asInt();
                for (JsonNode countryNode : countriesNode) {
                    Country country = objectMapper.treeToValue(countryNode, Country.class);
                    AgencyHasCountry agencyHasCountry = new AgencyHasCountry();
                    agencyHasCountry.setAgency_id(agencyId);
                    agencyHasCountry.setCountry_id(countryNode.get("id").asInt());
                    countriesList.add(agencyHasCountry);
                    countries.add(country);
                }
            }
        }
        if (!countries.isEmpty()) {
            jsonToCsvService.writeDataToCSV(countries, csvRawData.RAW_AGENCY_COUNTRIES_CSV, BATCH_SIZE);
            countries.clear();
        }
        if(!countriesList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(countriesList, csvRawData.LAUNCH_PROVIDERS_COUNTRIES_CSV, BATCH_SIZE);
            countriesList.clear();
        }
    }
    public List<SocialMedia> fetchSocialMediaLinks(JsonNode rootNode, ObjectMapper objectMapper) throws Exception {
        List<SocialMedia> socialMediaList = new ArrayList<>();
        JsonNode crewNodes = rootNode.get("launch_crew");
        if(crewNodes != null && !crewNodes.isEmpty() && crewNodes.isArray()) {
            for (JsonNode crewNode : crewNodes) {
                JsonNode socialMediaNodes = crewNode.get("astronaut").get("social_media_links");
                if (socialMediaNodes != null && !socialMediaNodes.isEmpty() && socialMediaNodes.isArray()) {
                    Integer astronautId = crewNode.get("astronaut").get("id").asInt();
                    for (JsonNode resultNode : socialMediaNodes) {
                        String mediaName = resultNode.get("social_media").get("name").asText();
                        SocialMedia socialMedia = objectMapper.treeToValue(resultNode, SocialMedia.class);
                        socialMedia.setMedia_name(mediaName);
                        socialMedia.setAstronaut_id(astronautId);
                        socialMediaList.add(socialMedia);
                    }
                }
            }
        }
        return socialMediaList;
    }
    public void fetchPrograms(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<Program> programList = new ArrayList<>();
        List<ProgramHasAgencies> programHasAgencies = new ArrayList<>();

        for (JsonNode resultNode : resultsNode) {
            String id = resultNode.get("id").asText();
            JsonNode programsNode = resultNode.get("program");
            if (programsNode != null && programsNode.isArray()) {
                for (JsonNode programNode : programsNode) {
                    Program program = objectMapper.treeToValue(programNode, Program.class);
                    program.setLaunch_id(id);
                    programList.add(program);
                    JsonNode agenciesNode = programNode.get("agencies");
                    if (agenciesNode != null && agenciesNode.isArray()) {
                        String programId = programNode.get("id").asText();
                        for (JsonNode agencyNode : agenciesNode) {
                            String agencyId = agencyNode.get("id").asText();
                            ProgramHasAgencies mapping = new ProgramHasAgencies(programId, agencyId);
                            programHasAgencies.add(mapping);
                        }
                    }
                }
            }
        }

        if (!programList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(programList, csvRawData.RAW_PROGRAMS_CSV, BATCH_SIZE);
            programList.clear();
        }
        if (!programHasAgencies.isEmpty()) {
            jsonToCsvService.writeDataToCSV(programHasAgencies, csvRawData.PROGRAMS_AGENCIES_CSV, BATCH_SIZE);
            programHasAgencies.clear();
        }
    }
    public void fetchUpdates(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<Updates> updatesList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode updatesNode = resultNode.get("updates");
            if (updatesNode != null && !updatesNode.isEmpty() && updatesNode.isArray()) {
                String id = resultNode.get("id").asText();
                for (JsonNode update : updatesNode) {
                    Updates updates = objectMapper.treeToValue(update, Updates.class);
                    updates.setLaunch_id(id);
                    updatesList.add(updates);
                }
            }
        }
        if (!updatesList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(updatesList, csvRawData.RAW_UPDATES_CSV, BATCH_SIZE);
            updatesList.clear();
        }
    }
    public void fetchVideo(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<Video> videoList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode videosNode = resultNode.get("vid_urls");
            if (videosNode != null && !videosNode.isEmpty() && videosNode.isArray()) {
                String id = resultNode.get("id").asText();
                for (JsonNode videoNode : videosNode) {
                    Video videoInfo = objectMapper.treeToValue(videoNode, Video.class);
                    videoInfo.setLaunch_id(id);
                    videoList.add(videoInfo);
                }
            }
        }
        if (!videoList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(videoList, csvRawData.RAW_VIDEO_CSV, BATCH_SIZE);
            videoList.clear();
        }
    }
    public void fetchMissionPatches(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<MissionPatches> patchesList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode patchesNode = resultNode.get("mission_patches");
            if (patchesNode != null && !patchesNode.isEmpty() && patchesNode.isArray()) {
                String id = resultNode.get("id").asText();
                for (JsonNode patchNode : patchesNode) {
                    MissionPatches missionPatch = objectMapper.treeToValue(patchNode, MissionPatches.class);
                    missionPatch.setLaunch_id(id);
                    patchesList.add(missionPatch);
                }
            }
        }
        if(!patchesList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(patchesList, csvRawData.RAW_MISSION_PATCHES_CSV, BATCH_SIZE);
            patchesList.clear();
        }
    }
    public void fetchInfoUrl(JsonNode resultsNode, ObjectMapper objectMapper) throws Exception {
        List<InfoUrl> infoUrlsList = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode infoURLsNode = resultNode.get("info_urls");
            if (infoURLsNode != null && !infoURLsNode.isEmpty() && infoURLsNode.isArray()) {
                String id = resultNode.get("id").asText();
                for (JsonNode infoUrlNode : infoURLsNode) {
                    InfoUrl infoUrl = objectMapper.treeToValue(infoUrlNode, InfoUrl.class);
                    infoUrl.setLaunch_id(id);
                    infoUrlsList.add(infoUrl);
                }
            }
        }
        if(!infoUrlsList.isEmpty()) {
            jsonToCsvService.writeDataToCSV(infoUrlsList, csvRawData.RAW_INFO_URLS_CSV, BATCH_SIZE);
            infoUrlsList.clear();
        }
    }
    public void saveLatestRocketImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            String id = resultNode.get("id").asText();
           // Integer configId = resultNode.path("rocket").path("configuration").get("id").asInt();
            JsonNode imageNode = resultNode.get("image");
            if (imageNode != null && !imageNode.isEmpty()){
                Image image = objectMapper.treeToValue(imageNode, Image.class);
                image.setLaunch_id(id);
               // image.setConfiguration_id(configId);
                images.add(image);
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_LAUNCH_ROCKET_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
    public void saveLaunchPadImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode imageNode = resultNode.get("pad").get("image");
            if (imageNode != null && !imageNode.isEmpty()){
                Integer id = resultNode.get("pad").get("id").asInt();
                Image image = objectMapper.treeToValue(imageNode, Image.class);
                image.setLaunch_pad_id(id);
                images.add(image);
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_PAD_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }

    public void saveRocketConfigImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode imageNode = resultNode.path("rocket").path("configuration").get("image");
            if (imageNode != null && !imageNode.isEmpty()){
                Integer id = resultNode.path("rocket").path("configuration").get("id").asInt();
                Image image = objectMapper.treeToValue(imageNode, Image.class);
                image.setConfiguration_id(id);
                images.add(image);
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_ROCKET_CONFIGURATION_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
    public void saveAgenciesImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            Integer id = resultNode.get("id").asInt();
            JsonNode imageNode = resultNode.get("logo");
            if (imageNode != null && !imageNode.isEmpty()){
                Image image = objectMapper.treeToValue(imageNode, Image.class);
                image.setAgency_id(id);
                images.add(image);
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_AGENCIES_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
    public void saveAstronautImages(JsonNode rootNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode crewNode : rootNode.get("launch_crew")){
            JsonNode imageNode = crewNode.get("astronaut").get("image");
            if (imageNode != null && !imageNode.isEmpty()){
                Integer id =  crewNode.get("astronaut").get("id").asInt();
                Image image = objectMapper.treeToValue(imageNode, Image.class);
                image.setAstronaut_id(id);
                images.add(image);
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_ASTRONAUT_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
    public void saveLauncherImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode launcherStageNodes = resultNode.get("rocket").get("launcher_stage");
            if (launcherStageNodes != null && !launcherStageNodes.isEmpty() && launcherStageNodes.isArray()){
                for (JsonNode stage : launcherStageNodes) {
                    JsonNode imageNode = stage.get("launcher").get("image");
                    if (imageNode != null && !imageNode.isEmpty()) {
                        Integer id = stage.get("launcher").get("id").asInt();
                        Image image = objectMapper.treeToValue(imageNode, Image.class);
                        image.setLauncher_id(id);
                        images.add(image);
                    }
                }
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_LAUNCHER_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
    public void saveSpacecraftConfigImages(JsonNode resultsNode, ObjectMapper objectMapper) throws IOException {
        List<Image> images = new ArrayList<>();
        for (JsonNode resultNode : resultsNode) {
            JsonNode spacecraftStageNodes =  resultNode.get("rocket").get("spacecraft_stage");
            if (spacecraftStageNodes != null && !spacecraftStageNodes.isEmpty() && spacecraftStageNodes.isArray()) {
                for (JsonNode stage : spacecraftStageNodes) {
                    Integer id = stage.path("spacecraft").path("spacecraft_config").get("id").asInt();
                    JsonNode imageNode = stage.path("spacecraft").path("spacecraft_config").get("image");
                    if (imageNode != null && !imageNode.isEmpty()) {
                        Image image = objectMapper.treeToValue(imageNode, Image.class);
                        image.setSpacecraft_id(id);
                        images.add(image);
                    }
                }
            }
        }
        if(!images.isEmpty()) {
            jsonToCsvService.writeDataToCSV(images, extractImages.RAW_SPACECRAFT_IMAGES_CSV, BATCH_SIZE);
            images.clear();
        }
    }
}
