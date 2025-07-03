package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.rocket.Rocket;
import com.moonkeyeu.core.api.launch.model.rocket.RocketConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RocketsRepository extends JpaRepository<RocketConfiguration, Long>, JpaSpecificationExecutor<RocketConfiguration> {
        @Query(""" 
           SELECT r
           FROM Rocket r
           LEFT JOIN FETCH r.rocketConfiguration rc
           LEFT JOIN FETCH rc.rocketConfImages
           LEFT JOIN FETCH r.spacecraftStages
           LEFT JOIN FETCH r.launcherStages sts
           LEFT JOIN FETCH sts.launcher
           LEFT JOIN FETCH sts.landing
           LEFT JOIN FETCH r.launches l
           LEFT JOIN FETCH l.launchStatus
           LEFT JOIN FETCH l.agencies a
           LEFT JOIN FETCH a.agencyType
           LEFT JOIN FETCH a.agenciesImages
           LEFT JOIN FETCH a.countries
           LEFT JOIN FETCH l.mission m
           LEFT JOIN FETCH m.orbit
           LEFT JOIN FETCH l.launchPad lp
           LEFT JOIN FETCH lp.location
           LEFT JOIN FETCH lp.launchPadImages
           LEFT JOIN FETCH lp.location
           LEFT JOIN FETCH l.missionPatches
           LEFT JOIN FETCH l.infoUrls
           LEFT JOIN FETCH l.videoUrls
           WHERE r.rocketId = :rocketId
        """)
        Optional<Rocket> findRocketWithRocketId(@Param("rocketId") Integer rocketId);
        @EntityGraph(value = "rocket-configuration-images-agency",  type = EntityGraph.EntityGraphType.FETCH)
        Page<RocketConfiguration> findAll(Specification<RocketConfiguration> specification, Pageable pageable);

}
