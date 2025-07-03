package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.pad.LaunchPad;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaunchPadRepository extends JpaRepository<LaunchPad, Long>, JpaSpecificationExecutor<LaunchPad> {

    @Query("""
            SELECT pd
            FROM LaunchPad pd
            LEFT JOIN FETCH pd.launchPadImages
            LEFT JOIN FETCH pd.launches l
            LEFT JOIN FETCH l.launchStatus
            LEFT JOIN FETCH l.launchPad lp
            LEFT JOIN FETCH lp.location
            LEFT JOIN FETCH l.rocket r
            LEFT JOIN FETCH r.rocketConfiguration rc
            LEFT JOIN FETCH l.infoUrls
            LEFT JOIN FETCH l.videoUrls
            WHERE pd.launchPadId = :launchPadId
    """)
    Optional<LaunchPad> findLaunchPadWithPadId(@Param("launchPadId") Integer launchPadId);

    @EntityGraph(value = "launch-pad-with-agencies-location-launches-images",  type = EntityGraph.EntityGraphType.FETCH)
    List<LaunchPad> findAll();

}
