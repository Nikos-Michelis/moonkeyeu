package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.launch.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaunchRepository extends JpaRepository<Launch, String>, JpaSpecificationExecutor<Launch> {

    @Query("""
            SELECT l
            FROM Launch l
            LEFT JOIN FETCH l.agencies a
            LEFT JOIN FETCH a.agencyType
            LEFT JOIN FETCH a.agenciesImages
            LEFT JOIN FETCH a.countries
            LEFT JOIN FETCH l.mission m
            LEFT JOIN FETCH m.orbit
            LEFT JOIN FETCH l.launchPad lp
            LEFT JOIN FETCH lp.location
            LEFT JOIN FETCH lp.launchPadImages
            LEFT JOIN FETCH l.rocket r
            LEFT JOIN FETCH r.rocketConfiguration rc
            LEFT JOIN FETCH rc.rocketConfImages
            LEFT JOIN FETCH r.spacecraftStages sps
            LEFT JOIN FETCH sps.spacecraft sp
            LEFT JOIN FETCH sps.landing spsl
            LEFT JOIN FETCH spsl.landingType
            LEFT JOIN FETCH spsl.landingZone
            LEFT JOIN FETCH sp.status
            LEFT JOIN FETCH sp.spacecraftConfiguration spc
            LEFT JOIN FETCH spc.spacecraftType
            LEFT JOIN FETCH spc.spacecraftConfImages
            LEFT JOIN FETCH sps.crewMembers cm
            LEFT JOIN FETCH cm.astronaut ast
            LEFT JOIN FETCH ast.status
            LEFT JOIN FETCH ast.astronautImages
            LEFT JOIN FETCH ast.socialMedia
            LEFT JOIN FETCH ast.countries
            LEFT JOIN FETCH cm.role
            LEFT JOIN FETCH r.launcherStages lrss
            LEFT JOIN FETCH lrss.launcher lrs
            LEFT JOIN FETCH lrs.status
            LEFT JOIN FETCH lrs.launcherImages
            LEFT JOIN FETCH lrss.landing lds
            LEFT JOIN FETCH lds.landingType
            LEFT JOIN FETCH lds.landingZone lz
            LEFT JOIN FETCH lz.location
            LEFT JOIN FETCH l.missionPatches mp
            LEFT JOIN FETCH l.launchStatus
            LEFT JOIN FETCH l.infoUrls
            LEFT JOIN FETCH l.videoUrls
            WHERE l.launchId = :launchId
    """)
    Optional<Launch> findLaunchWithLaunchId(@Param("launchId") String launchId);
    @Query("""
      SELECT l
      FROM Launch l
      LEFT JOIN FETCH l.agencies a
      LEFT JOIN FETCH a.agencyType
      LEFT JOIN FETCH a.agenciesImages
      LEFT JOIN FETCH a.countries
      LEFT JOIN FETCH l.mission m
      LEFT JOIN FETCH m.orbit
      LEFT JOIN FETCH l.launchPad lp
      LEFT JOIN FETCH lp.location
      LEFT JOIN FETCH lp.launchPadImages
      LEFT JOIN FETCH l.rocket r
      LEFT JOIN FETCH r.rocketConfiguration rc
      LEFT JOIN FETCH rc.rocketConfImages
      LEFT JOIN FETCH r.spacecraftStages sps
      LEFT JOIN FETCH sps.spacecraft sp
      LEFT JOIN FETCH sps.landing spsl
      LEFT JOIN FETCH spsl.landingType
      LEFT JOIN FETCH spsl.landingZone
      LEFT JOIN FETCH sp.status
      LEFT JOIN FETCH sp.spacecraftConfiguration spc
      LEFT JOIN FETCH spc.spacecraftType
      LEFT JOIN FETCH spc.spacecraftConfImages
      LEFT JOIN FETCH sps.crewMembers cm
      LEFT JOIN FETCH cm.astronaut ast
      LEFT JOIN FETCH ast.status
      LEFT JOIN FETCH ast.astronautImages
      LEFT JOIN FETCH ast.socialMedia
      LEFT JOIN FETCH ast.countries
      LEFT JOIN FETCH cm.role
      LEFT JOIN FETCH r.launcherStages lrss
      LEFT JOIN FETCH lrss.launcher lrs
      LEFT JOIN FETCH lrs.status
      LEFT JOIN FETCH lrs.launcherImages
      LEFT JOIN FETCH lrss.landing lds
      LEFT JOIN FETCH lds.landingType
      LEFT JOIN FETCH lds.landingZone lz
      LEFT JOIN FETCH lz.location
      LEFT JOIN FETCH l.missionPatches mp
      LEFT JOIN FETCH l.launchStatus
      LEFT JOIN FETCH l.infoUrls
      LEFT JOIN FETCH l.videoUrls
      LEFT JOIN FETCH l.bookmarks b
      LEFT JOIN FETCH b.user u
      WHERE l.launchId = :launchId AND b.bookmarkId = :bookmarkId
    """)
    Optional<Launch> findLaunchWithBookmarkIdAndLaunchId(@Param("bookmarkId") Long bookmarkId, @Param("launchId") String launchId);
    @Query("""
        SELECT l
        FROM Launch l
        LEFT JOIN FETCH l.agencies a
        LEFT JOIN FETCH a.agencyType
        LEFT JOIN FETCH a.agenciesImages
        LEFT JOIN FETCH a.countries
        LEFT JOIN FETCH l.mission m
        LEFT JOIN FETCH m.orbit
        LEFT JOIN FETCH l.launchPad lp
        LEFT JOIN FETCH lp.location
        LEFT JOIN FETCH lp.launchPadImages
        LEFT JOIN FETCH l.rocket r
        LEFT JOIN FETCH r.rocketConfiguration rc
        LEFT JOIN FETCH rc.rocketConfImages
        LEFT JOIN FETCH r.spacecraftStages sps
        LEFT JOIN FETCH sps.spacecraft sp
        LEFT JOIN FETCH sps.landing spsl
        LEFT JOIN FETCH spsl.landingType
        LEFT JOIN FETCH spsl.landingZone
        LEFT JOIN FETCH sp.status
        LEFT JOIN FETCH sp.spacecraftConfiguration spc
        LEFT JOIN FETCH spc.spacecraftType
        LEFT JOIN FETCH spc.spacecraftConfImages
        LEFT JOIN FETCH sps.crewMembers cm
        LEFT JOIN FETCH cm.astronaut ast
        LEFT JOIN FETCH ast.status
        LEFT JOIN FETCH ast.astronautImages
        LEFT JOIN FETCH ast.socialMedia
        LEFT JOIN FETCH ast.countries
        LEFT JOIN FETCH cm.role
        LEFT JOIN FETCH r.launcherStages lrss
        LEFT JOIN FETCH lrss.launcher lrs
        LEFT JOIN FETCH lrs.status
        LEFT JOIN FETCH lrs.launcherImages
        LEFT JOIN FETCH lrss.landing lds
        LEFT JOIN FETCH lds.landingType
        LEFT JOIN FETCH lds.landingZone lz
        LEFT JOIN FETCH lz.location
        LEFT JOIN FETCH l.missionPatches mp
        LEFT JOIN FETCH l.launchStatus
        LEFT JOIN FETCH l.infoUrls
        LEFT JOIN FETCH l.videoUrls
        WHERE a.agencyId = :agencyId AND l.net > CURRENT_TIMESTAMP
        ORDER BY l.net ASC
        LIMIT 1
    """)
    Optional<Launch> findUpcomingLaunchesByAgencyId(@Param("agencyId") Integer agencyId);
    @Query("""
        SELECT l
        FROM Launch l
        LEFT JOIN FETCH l.agencies a
        LEFT JOIN FETCH a.agencyType
        LEFT JOIN FETCH a.agenciesImages
        LEFT JOIN FETCH a.countries
        LEFT JOIN FETCH l.mission m
        LEFT JOIN FETCH m.orbit
        LEFT JOIN FETCH l.programs p
        LEFT JOIN FETCH l.launchPad lp
        LEFT JOIN FETCH lp.location
        LEFT JOIN FETCH lp.launchPadImages
        LEFT JOIN FETCH l.rocket r
        LEFT JOIN FETCH r.rocketConfiguration rc
        LEFT JOIN FETCH rc.rocketConfImages
        LEFT JOIN FETCH r.spacecraftStages sps
        LEFT JOIN FETCH sps.spacecraft sp
        LEFT JOIN FETCH sps.landing spsl
        LEFT JOIN FETCH spsl.landingType
        LEFT JOIN FETCH spsl.landingZone
        LEFT JOIN FETCH sp.status
        LEFT JOIN FETCH sp.spacecraftConfiguration spc
        LEFT JOIN FETCH spc.spacecraftType
        LEFT JOIN FETCH spc.spacecraftConfImages
        LEFT JOIN FETCH sps.crewMembers cm
        LEFT JOIN FETCH cm.astronaut ast
        LEFT JOIN FETCH ast.status
        LEFT JOIN FETCH ast.astronautImages
        LEFT JOIN FETCH ast.socialMedia
        LEFT JOIN FETCH ast.countries
        LEFT JOIN FETCH cm.role
        LEFT JOIN FETCH r.launcherStages lrss
        LEFT JOIN FETCH lrss.launcher lrs
        LEFT JOIN FETCH lrs.status
        LEFT JOIN FETCH lrs.launcherImages
        LEFT JOIN FETCH lrss.landing lds
        LEFT JOIN FETCH lds.landingType
        LEFT JOIN FETCH lds.landingZone lz
        LEFT JOIN FETCH lz.location
        LEFT JOIN FETCH l.missionPatches mp
        LEFT JOIN FETCH l.launchStatus
        LEFT JOIN FETCH l.infoUrls
        LEFT JOIN FETCH l.videoUrls
        WHERE p.programId = :programId AND l.net >= CURRENT_TIMESTAMP
        ORDER BY l.net ASC
        LIMIT 1
    """)
    Optional<Launch> findUpcomingLaunchesByProgramId(@Param("programId") Integer programId);

    Page<Launch> findAll(Specification<Launch> specification, Pageable pageable);

}
