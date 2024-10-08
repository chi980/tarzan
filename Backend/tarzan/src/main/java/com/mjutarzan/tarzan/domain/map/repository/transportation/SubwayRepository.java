package com.mjutarzan.tarzan.domain.map.repository.transportation;

import com.mjutarzan.tarzan.domain.map.entity.transportation.Subway;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubwayRepository extends JpaRepository<Subway, Long> {
    @Query("SELECT s FROM Subway s WHERE ST_DWithin(s.location, :location, :radius) = true")
    List<Subway> findAllWithinRadius(Point location, double radius);
}
