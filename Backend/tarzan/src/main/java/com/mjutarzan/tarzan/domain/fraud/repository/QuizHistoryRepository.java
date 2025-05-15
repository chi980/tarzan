package com.mjutarzan.tarzan.domain.fraud.repository;

import com.mjutarzan.tarzan.domain.fraud.entity.QuizHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface QuizHistoryRepository extends JpaRepository<QuizHistory, Long> {

    Boolean existsByUserIdAndSolvedDate(Long id, LocalDate now);
}
