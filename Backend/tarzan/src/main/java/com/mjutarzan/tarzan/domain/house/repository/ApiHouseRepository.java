package com.mjutarzan.tarzan.domain.house.repository;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.map.entity.Building;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Bicycle;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApiHouseRepository extends JpaRepository<ApiHouse, Long> {

    @Query(value = "SELECT * FROM house h " +
            "WHERE ST_DistanceSphere(h.house_location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)) <= :radius",
            nativeQuery = true)
    List<ApiHouse> findAllWithinRadius(@Param("longitude") double longitude,
                                       @Param("latitude") double latitude,
                                       @Param("radius") double radius);


    @Query("SELECT ah FROM ApiHouse ah WHERE LOWER(ah.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(ah.address) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<ApiHouse> findByNameContaining(@Param("search") String search, Pageable pageable);
}
