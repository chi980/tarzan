package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.Park;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkRepository extends JpaRepository<Park, Long> {

    @Query("SELECT p FROM Park p WHERE ST_DWithin(p.location, :location, :radius) = true")
    List<Park> findAllWithinRadius(Point location, double radius);
}
