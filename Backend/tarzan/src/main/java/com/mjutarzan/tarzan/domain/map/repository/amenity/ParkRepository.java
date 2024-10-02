package com.mjutarzan.tarzan.domain.map.repository.amenity;

import com.mjutarzan.tarzan.domain.map.entity.amenity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {
}
