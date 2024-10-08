package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.Gym;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {

    @Query("SELECT g FROM Gym g WHERE ST_DWithin(g.location, :location, :radius) = true")
    List<Gym> findAllWithinRadius(Point location, double radius);
}
