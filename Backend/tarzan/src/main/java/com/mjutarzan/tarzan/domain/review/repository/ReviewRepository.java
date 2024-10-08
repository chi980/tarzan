package com.mjutarzan.tarzan.domain.review.repository;

import com.mjutarzan.tarzan.domain.review.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.writer.id = :writerId ORDER BY r.createdDate DESC")
    List<Review> findByWriterLimit3(@Param("writerId") Long writerId);
}
