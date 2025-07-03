package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.launcher.Launcher;
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
public interface LauncherRepository extends JpaRepository<Launcher, Long>, JpaSpecificationExecutor<Launcher> {
        @Query("""
             SELECT l
             FROM Launcher l
             LEFT JOIN FETCH l.status
             LEFT JOIN FETCH l.launcherStages lrs
             LEFT JOIN FETCH l.launcherImages
             LEFT JOIN FETCH lrs.type
             LEFT JOIN FETCH lrs.rocket r
             LEFT JOIN FETCH lrs.landing
             LEFT JOIN FETCH l.status
             LEFT JOIN FETCH l.launcherImages
             LEFT JOIN FETCH r.launches ls
             WHERE l.launcherId = :launcherId  
        """)
        Optional<Launcher> findLauncherWithLauncherId(@Param("launcherId") Integer launcherId);
        @EntityGraph(value = "launcher-launcherStage-status-images",  type = EntityGraph.EntityGraphType.FETCH)
        Page<Launcher> findAll(Specification<Launcher> specification, Pageable pageable);

}
