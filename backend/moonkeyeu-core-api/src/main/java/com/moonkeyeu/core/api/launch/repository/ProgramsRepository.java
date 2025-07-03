package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.program.Programs;
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
public interface ProgramsRepository extends JpaRepository<Programs, Long>, JpaSpecificationExecutor<Programs> {
    @Query("""
            SELECT p FROM Programs p
            LEFT JOIN FETCH p.type
            LEFT JOIN FETCH p.agencies a
            LEFT JOIN FETCH p.programImages
            LEFT JOIN FETCH a.agencyType
            LEFT JOIN FETCH a.agenciesImages
            LEFT JOIN FETCH p.launches l
            LEFT JOIN FETCH l.agencies
            LEFT JOIN FETCH l.rocket r
            LEFT JOIN FETCH r.rocketConfiguration rc
            LEFT JOIN FETCH l.launchPad lp
            LEFT JOIN FETCH lp.location
            LEFT JOIN FETCH l.launchStatus
            LEFT JOIN FETCH l.infoUrls
            LEFT JOIN FETCH l.videoUrls
            WHERE p.programId = :programId
    """)
    Optional<Programs> findProgramById(@Param("programId") Integer programId);
    @EntityGraph(value = "programs-agencies-launches", type = EntityGraph.EntityGraphType.FETCH)
    Page<Programs> findAll(Specification<Programs> specification, Pageable pageable);
}
