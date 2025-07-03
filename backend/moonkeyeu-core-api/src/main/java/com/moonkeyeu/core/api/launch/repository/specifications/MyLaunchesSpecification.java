package com.moonkeyeu.core.api.launch.repository.specifications;

import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import com.moonkeyeu.core.api.launch.model.spacecraft.Spacecraft;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftStage;
import com.moonkeyeu.core.api.user.model.Bookmark;
import com.moonkeyeu.core.api.user.model.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class MyLaunchesSpecification {

    public static Specification<Launch> rootSpecification() {
        return (Root<Launch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("launchStatus", JoinType.LEFT);
                root.fetch("agencies", JoinType.LEFT);
                root.fetch("launchPad", JoinType.LEFT)
                        .fetch("location", JoinType.LEFT);
                root.fetch("rocket", JoinType.LEFT)
                        .fetch("rocketConfiguration", JoinType.LEFT);
                       // .fetch("rocketConfImages", JoinType.LEFT);
                root.fetch("missionPatches", JoinType.LEFT);
                root.fetch("videoUrls", JoinType.LEFT);
                root.fetch("infoUrls", JoinType.LEFT);
            }

            return builder.conjunction();
        };
    }

    public static Specification<Launch> hasUserIdAndBookmarkName(Long userId, String bookmarkName) {
        return (root, query, builder) -> {
            Join<Launch, Bookmark> bookmarksJoin = root.join("bookmarks", JoinType.LEFT);
            Join<Bookmark, User> userJoin = bookmarksJoin.join("user", JoinType.LEFT);
            Predicate userIdPredicate = builder.equal(userJoin.get("userId"), userId);
            Predicate bookmarkNamePredicate = builder.equal(bookmarksJoin.get("bookmarkName"), bookmarkName);
            return builder.and(userIdPredicate, bookmarkNamePredicate);
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
