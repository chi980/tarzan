package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CivicCenterRepository extends JpaRepository<CivicCenter, Long> {
    @Query("SELECT c FROM CivicCenter c WHERE ST_DWithin(c.location, :location, :radius) = true")
    List<CivicCenter> findAllWithinRadius(Point location, double radius);
}
