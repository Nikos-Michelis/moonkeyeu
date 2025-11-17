package com.moonkeyeu.core.api.launch.repository;

import com.moonkeyeu.core.api.launch.model.CelestialBody;
import com.moonkeyeu.core.api.launch.model.agency.Agencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelestialBodiesRepository extends JpaRepository<CelestialBody, Long> {

    @Query("SELECT cb FROM CelestialBody cb INNER JOIN FETCH cb.type")
    List<CelestialBody> findAll();
}
