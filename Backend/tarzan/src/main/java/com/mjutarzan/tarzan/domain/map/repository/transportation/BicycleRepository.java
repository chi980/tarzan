package com.mjutarzan.tarzan.domain.map.repository.transportation;

import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Bicycle;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BicycleRepository extends JpaRepository<Bicycle, Long> {


    @Query(value = "SELECT * FROM building b WHERE ST_DWithin(b.building_location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) = true AND b.building_type = 'bicycle'", nativeQuery = true)
    List<CivicCenter> findAllWithinRadius(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("radius") double radius);
}
