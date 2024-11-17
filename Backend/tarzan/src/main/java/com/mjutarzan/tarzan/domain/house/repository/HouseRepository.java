package com.mjutarzan.tarzan.domain.house.repository;

import com.mjutarzan.tarzan.domain.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
