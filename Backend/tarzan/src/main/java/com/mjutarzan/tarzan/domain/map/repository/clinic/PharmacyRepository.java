package com.mjutarzan.tarzan.domain.map.repository.clinic;

import com.mjutarzan.tarzan.domain.map.entity.clinic.Pharmacy;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query("SELECT p FROM Pharmacy p WHERE ST_DWithin(p.location, :location, :radius) = true")
    List<Pharmacy> findAllWithinRadius(Point location, double radius);
}
