package com.mjutarzan.tarzan.domain.map.repository.security;

import com.mjutarzan.tarzan.domain.map.entity.security.Police;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoliceRepository extends JpaRepository<Police, Long> {

    @Query("SELECT p FROM Police p WHERE ST_DWithin(p.location, :location, :radius) = true")
    List<Police> findAllWithinRadius(Point location, double radius);
}
