package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.Gym;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {

    @Query("SELECT b FROM Gym b " +
            "WHERE function('ST_DWithin', b.location, function('ST_SetSRID', function('ST_MakePoint', :longitude, :latitude), 4326), :radius) = true")
    List<Gym> findAllWithinRadius(@Param("longitude") double longitude,
                                          @Param("latitude") double latitude,
                                          @Param("radius") double radius);

//    @Query(value = "SELECT * FROM building b WHERE ST_DWithin(b.building_location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) = true AND b.building_type = 'gym'", nativeQuery = true)
//    List<CivicCenter> findAllWithinRadius(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("radius") double radius);
}
