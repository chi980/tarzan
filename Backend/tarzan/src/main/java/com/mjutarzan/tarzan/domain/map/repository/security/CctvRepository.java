package com.mjutarzan.tarzan.domain.map.repository.security;

import com.mjutarzan.tarzan.domain.map.entity.security.Cctv;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CctvRepository extends JpaRepository<Cctv, Long> {

    @Query("SELECT c FROM Cctv c WHERE ST_DWithin(c.location, :location, :radius) = true")
    List<Cctv> findAllWithinRadius(Point location, double radius);
}
