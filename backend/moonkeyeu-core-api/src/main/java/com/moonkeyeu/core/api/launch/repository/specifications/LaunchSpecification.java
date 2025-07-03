package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.launcher.Launcher;
import com.moonkeyeu.core.api.launch.model.launcher.LauncherStage;
import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import com.moonkeyeu.core.api.launch.model.spacecraft.Spacecraft;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftStage;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class LaunchSpecification {
    public static Specification<Launch> rootSpecification() {
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("launchStatus", JoinType.LEFT);
                root.fetch("agencies", JoinType.LEFT);
                root.fetch("launchPad", JoinType.LEFT)
                        .fetch("location", JoinType.LEFT);
                root.fetch("rocket", JoinType.LEFT)
                        .fetch("rocketConfiguration", JoinType.LEFT);
                root.fetch("missionPatches", JoinType.LEFT);
                root.fetch("videoUrls", JoinType.LEFT);
                root.fetch("infoUrls", JoinType.LEFT);
            }

            return builder.conjunction(); // where = 1 = 1
        };
    }

    public static Specification<Launch> hasLauncherSerialNumber(String launcherId) {
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Launch, Rocket> rocketJoin = root.join("rocket", JoinType.LEFT);
            Join<Rocket, LauncherStage> launcherStagesJoin = rocketJoin.join("launcherStages", JoinType.LEFT);
            Join<LauncherStage, Launcher> launcherJoin = launcherStagesJoin.join("launcher", JoinType.LEFT);
            return builder.equal(launcherJoin.get("launcherId"), launcherId);
        };
    }


    public static Specification<Launch> isUpcomingLaunch(Boolean upcoming){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if(upcoming) {
                return builder.greaterThanOrEqualTo(root.get("net"), Instant.now());
            }
            return builder.lessThanOrEqualTo(root.get("net"), Instant.now());
        };
    }
    public static Specification<Launch> hasRocketConfiguration(String rocketConfigId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> rocket = root.join("rocket", JoinType.LEFT);
            Join<Object, Object> rocketConfiguration = rocket.join("rocketConfiguration", JoinType.LEFT);
            return builder.equal(rocketConfiguration.get("rocketConfId"), rocketConfigId);
        };
    }
    public static Specification<Launch> hasSpacecraftConfiguration(String spacecraftConfigId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> rocket = root.join("rocket", JoinType.LEFT);
            Join<Object, Object> spacecraftStage = rocket.join("spacecraftStages", JoinType.LEFT);
            Join<Object, Object> spacecraft = spacecraftStage.join("spacecraft", JoinType.LEFT);
            Join<Object, Object> spacecraftConfiguration = spacecraft.join("spacecraftConfiguration", JoinType.LEFT);
            return builder.equal(spacecraftConfiguration.get("spacecraftConfId"), spacecraftConfigId);
        };
    }
    public static Specification<Launch> hasAgency(String agencyId) {
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> agency = root.join("agencies", JoinType.LEFT);
            return builder.equal(agency.get("agencyId"), agencyId);
        };
    }
    public static Specification<Launch> hasAstronaut(String astronautId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> rocket = root.join("rocket", JoinType.LEFT);
            Join<Object, Object> spacecraftStage = rocket.join("spacecraftStages", JoinType.LEFT);
            Join<Object, Object> crewMembers = spacecraftStage.join("crewMembers", JoinType.LEFT);
            Join<Object, Object> astronaut = crewMembers.join("astronaut", JoinType.LEFT);
            return builder.equal(astronaut.get("astronautId"), astronautId);
        };
    }
   public static Specification<Launch> hasLocation(String locationId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> launchPad = root.join("launchPad", JoinType.LEFT);
            Join<Object, Object> location = launchPad.join("location", JoinType.LEFT);
            return builder.equal(location.get("locationId"), locationId);
        };
   }
    public static Specification<Launch> hasLaunchPad(String launchPadId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> launchPad = root.join("launchPad", JoinType.LEFT);
            return builder.equal(launchPad.get("launchPadId"), launchPadId);
        };
    }
    public static Specification<Launch> hasProgram(String locationId){
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Object, Object> program = root.join("programs", JoinType.LEFT);
            return builder.equal(program.get("programId"), locationId);
        };
    }

   public static Specification<Launch> hasSearchKey(String key) {
       String searchKeyPattern = "%" + key.toLowerCase() + "%";
       return (root, query, cb) -> {
           Join<Launch, Agencies> agencyJoin = root.join("agencies", JoinType.LEFT);
           Join<Launch, Rocket> rocketJoin = root.join("rocket", JoinType.LEFT);
           Join<Launch, RocketConfiguration> rocketConfJoin = rocketJoin.join("rocketConfiguration", JoinType.LEFT);
           Join<Launch, SpacecraftStage> spacecraftStageJoin = rocketJoin.join("spacecraftStages", JoinType.LEFT);
           Join<Launch, Spacecraft>  spacecraftJoin = spacecraftStageJoin.join("spacecraft", JoinType.LEFT);
           Join<Launch, SpacecraftConfiguration> spacecraftConfJoin = spacecraftJoin.join("spacecraftConfiguration", JoinType.LEFT);

           Predicate searchByName = cb.like(cb.lower(root.get("launchName")), searchKeyPattern);
           Predicate searchBySlug = cb.like(cb.lower(root.get("slug")), searchKeyPattern);

           Predicate searchByAgencyName = cb.like(cb.lower(agencyJoin.get("agencyName")), searchKeyPattern);
           Predicate searchByRocketName = cb.like(cb.lower(rocketConfJoin.get("rocketName")), searchKeyPattern);
           Predicate searchByRocketVariant = cb.like(cb.lower(rocketConfJoin.get("variant")), searchKeyPattern);
           Predicate searchBySpacecraftConfName = cb.like(cb.lower(spacecraftConfJoin.get("spacecraftConfName")), searchKeyPattern);

           return cb.or(
                   searchByName,
                   searchBySlug,
                   searchByAgencyName,
                   searchByRocketName,
                   searchByRocketVariant,
                   searchBySpacecraftConfName
           );
       };
   }
}
