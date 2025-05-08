package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.Amenity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Query(value = "SELECT * FROM building b " +
            "WHERE ST_DistanceSphere(b.building_location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) <= :radius " +
            "AND b.building_type = 'amenity'",
            nativeQuery = true)
    List<Amenity> findAllWithinRadius(@Param("longitude") double longitude,
                                      @Param("latitude") double latitude,
                                      @Param("radius") double radius);

    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM building b " +
                    "WHERE ST_DistanceSphere(" +
                    "        b.building_location, " +
                    "        ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)" +
                    "      ) <= :radius " +
                    "  AND b.building_type in ('civic_center', 'gym','park' )",
            nativeQuery = true)
    Long countAllWithinRadius(
            @Param("longitude") double longitude,
            @Param("latitude")  double latitude,
            @Param("radius")    double radius
    );
}
