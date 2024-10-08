package com.mjutarzan.tarzan.domain.map.repository.shopping;

import com.mjutarzan.tarzan.domain.map.entity.shopping.Mart;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MartRepository extends JpaRepository<Mart, Long> {

    @Query("SELECT m FROM Mart m WHERE ST_DWithin(m.location, :location, :radius) = true")
    List<Mart> findAllWithinRadius(Point location, double radius);
}
