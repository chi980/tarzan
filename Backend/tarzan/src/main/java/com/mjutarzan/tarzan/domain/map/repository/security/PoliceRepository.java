package com.mjutarzan.tarzan.domain.map.repository.security;

import com.mjutarzan.tarzan.domain.map.entity.security.Police;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliceRepository extends JpaRepository<Police, Long> {
}
