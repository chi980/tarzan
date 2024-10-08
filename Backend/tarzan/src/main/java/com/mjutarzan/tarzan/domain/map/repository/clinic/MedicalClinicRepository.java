package com.mjutarzan.tarzan.domain.map.repository.clinic;

import com.mjutarzan.tarzan.domain.map.entity.clinic.MedicalClinic;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalClinicRepository extends JpaRepository<MedicalClinic, Long> {
    @Query("SELECT mc FROM MedicalClinic mc WHERE ST_DWithin(mc.location, :location, :radius) = true")
    List<MedicalClinic> findAllWithinRadius(Point location, double radius);
}
