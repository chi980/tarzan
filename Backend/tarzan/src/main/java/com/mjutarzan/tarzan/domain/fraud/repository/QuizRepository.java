package com.mjutarzan.tarzan.domain.fraud.repository;

import com.mjutarzan.tarzan.domain.fraud.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
