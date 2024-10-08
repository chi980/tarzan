package com.mjutarzan.tarzan.domain.map.repository;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query(value = "SELECT * FROM building b WHERE ST_DWithin(b.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) = true", nativeQuery = true)
    List<Building> findAllWithinRadius(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("radius") double radius);
}
