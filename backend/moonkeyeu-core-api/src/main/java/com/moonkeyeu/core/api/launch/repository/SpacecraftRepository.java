package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.spacecraft.SpacecraftConfiguration;
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
public interface SpacecraftRepository extends JpaRepository<SpacecraftConfiguration, Long>, JpaSpecificationExecutor<SpacecraftConfiguration> {

    @Query("""
            SELECT s
            FROM SpacecraftConfiguration s
            LEFT JOIN FETCH s.spacecraftType
            LEFT JOIN FETCH s.spacecraftConfImages
            WHERE s.spacecraftConfId = :spacecraftConfId
    """)
    Optional<SpacecraftConfiguration> findSpacecraftWithSpacecraftId(@Param("spacecraftConfId") Integer spacecraftConfId);
    @EntityGraph(value = "spacecraft-configuration-images",  type = EntityGraph.EntityGraphType.LOAD)
    Page<SpacecraftConfiguration> findAll(Specification<SpacecraftConfiguration> specification, Pageable pageable);

}
