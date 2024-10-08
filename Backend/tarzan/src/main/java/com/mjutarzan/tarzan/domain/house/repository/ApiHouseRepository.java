package com.mjutarzan.tarzan.domain.house.repository;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApiHouseRepository extends JpaRepository<ApiHouse, Long> {
//    @Query("SELECT ah FROM ApiHouse ah WHERE ST_DWithin(ah.location, :location, :radius) = true")
//    List<ApiHouse> findAllWithinRadius(Point location, Double radius);

    @Query("SELECT ah FROM ApiHouse ah WHERE LOWER(ah.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<ApiHouse> findByNameContaining(@Param("search") String search, Pageable pageable);
}
