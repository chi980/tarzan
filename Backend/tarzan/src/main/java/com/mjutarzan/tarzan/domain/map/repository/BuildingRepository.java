package com.mjutarzan.tarzan.domain.map.repository;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    @Query("SELECT b FROM Building b WHERE ST_DWithin(b.location, :location, :radius) = true")
    List<Building> findAllWithinRadius(Point location, double radius);
}
