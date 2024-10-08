package com.mjutarzan.tarzan.domain.map.repository.transportation;

import com.mjutarzan.tarzan.domain.map.entity.transportation.Bus;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("SELECT b FROM Bus b WHERE ST_DWithin(b.location, :location, :radius) = true")
    List<Bus> findAllWithinRadius(Point location, double radius);
}
