package com.mjutarzan.tarzan.domain.map.repository.transportation;

import com.mjutarzan.tarzan.domain.map.entity.transportation.Bicycle;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BicycleRepository extends JpaRepository<Bicycle, Long> {
    @Query("SELECT b FROM Bicycle b WHERE ST_DWithin(b.location, :location, :radius) = true")
    List<Bicycle> findAllWithinRadius(Point location, double radius);
}
