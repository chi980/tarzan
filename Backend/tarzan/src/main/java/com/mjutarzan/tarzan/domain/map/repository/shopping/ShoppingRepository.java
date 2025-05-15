package com.mjutarzan.tarzan.domain.map.repository.shopping;

import com.mjutarzan.tarzan.domain.map.entity.shopping.Shopping;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingRepository extends JpaRepository<Shopping, Long> {
    @Query(value =
            "SELECT COUNT(*) " +
                    "FROM building b " +
                    "WHERE ST_DistanceSphere(" +
                    "        b.building_location, " +
                    "        ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)" +
                    "      ) <= :radius " +
                    "  AND b.building_type in ('convenience_store', 'mart' )",
            nativeQuery = true)
    Long countAllWithinRadius(
            @Param("longitude") double longitude,
            @Param("latitude")  double latitude,
            @Param("radius")    double radius
    );
}
