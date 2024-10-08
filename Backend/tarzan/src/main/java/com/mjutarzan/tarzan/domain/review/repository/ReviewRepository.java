package com.mjutarzan.tarzan.domain.review.repository;

import com.mjutarzan.tarzan.domain.review.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM Review r WHERE r.writer.id = :writerId ORDER BY r.createdAt DESC LIMIT 3", nativeQuery = true)
    List<Review> findByWriterLimit3(@Param("writerId") Long writerId);


    @Query(value = "SELECT * FROM review WHERE house = :houseId", nativeQuery = true)
    Page<Review> findReviewsByHouseId(@Param("houseId") Long houseId, Pageable pageable);
}
