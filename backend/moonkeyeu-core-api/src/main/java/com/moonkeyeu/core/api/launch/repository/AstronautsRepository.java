package com.moonkeyeu.core.api.launch.repository;


import com.moonkeyeu.core.api.launch.model.astronaut.Astronaut;
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
public interface AstronautsRepository extends JpaRepository<Astronaut, Long>, JpaSpecificationExecutor<Astronaut> {


    @Query("""
        SELECT a
        FROM Astronaut a
        LEFT JOIN FETCH a.status
        LEFT JOIN FETCH a.astronautImages
        LEFT JOIN FETCH a.socialMedia
        LEFT JOIN FETCH a.countries
        LEFT JOIN FETCH a.agency ag
        LEFT JOIN FETCH ag.agencyType
        LEFT JOIN FETCH ag.agenciesImages
        LEFT JOIN FETCH a.crewMembers cm
        LEFT JOIN FETCH cm.role
        LEFT JOIN FETCH cm.launch l
        LEFT JOIN FETCH l.launchStatus
        LEFT JOIN FETCH l.launchPad lp
        LEFT JOIN FETCH l.agencies
        LEFT JOIN FETCH lp.location
        LEFT JOIN FETCH l.rocket r
        LEFT JOIN FETCH r.rocketConfiguration rc
        LEFT JOIN FETCH l.videoUrls
        LEFT JOIN FETCH l.infoUrls
        WHERE a.astronautId = :astronautId
    """)
    Optional<Astronaut> findAstronautAndLaunchByAstronautId(@Param("astronautId") Integer astronautId);


    @EntityGraph(value = "astronaut-with-status-images-nationality-socialmedia", type = EntityGraph.EntityGraphType.FETCH)
    Page<Astronaut> findAll(Specification<Astronaut> specification, Pageable pageable);

}

