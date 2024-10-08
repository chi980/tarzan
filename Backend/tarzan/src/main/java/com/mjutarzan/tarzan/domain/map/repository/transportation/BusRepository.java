package com.mjutarzan.tarzan.domain.map.repository.transportation;

import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Bus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {


    @Query(value = "SELECT * FROM bus b WHERE ST_DWithin(b.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) = true", nativeQuery = true)
    List<CivicCenter> findAllWithinRadius(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("radius") double radius);
}
