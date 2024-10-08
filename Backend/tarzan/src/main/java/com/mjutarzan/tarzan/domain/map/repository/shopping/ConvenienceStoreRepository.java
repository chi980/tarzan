package com.mjutarzan.tarzan.domain.map.repository.shopping;

import com.mjutarzan.tarzan.domain.map.entity.shopping.ConvenienceStore;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConvenienceStoreRepository extends JpaRepository<ConvenienceStore, Long> {
    @Query("SELECT c FROM ConvenienceStore c WHERE ST_DWithin(c.location, :location, :radius) = true")
    List<ConvenienceStore> findAllWithinRadius(Point location, double radius);
}
