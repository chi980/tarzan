package com.mjutarzan.tarzan.domain.map.repository.security;

import com.mjutarzan.tarzan.domain.map.entity.security.Security;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecurityRepository extends JpaRepository<Security, Long> {
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM building b " +
                    "WHERE ST_DistanceSphere(" +
                    "        b.building_location, " +
                    "        ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)" +
                    "      ) <= :radius " +
                    "  AND b.building_type in ('cctv', 'police' )",
            nativeQuery = true)
    Long countAllWithinRadius(
            @Param("longitude") double longitude,
            @Param("latitude")  double latitude,
            @Param("radius")    double radius
    );
}
