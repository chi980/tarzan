package com.mjutarzan.tarzan.domain.review.repository;

import com.mjutarzan.tarzan.domain.review.entity.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review r WHERE r.review_house = :houseId ORDER BY r.created_at DESC LIMIT 3", nativeQuery = true)
    List<Review> findByHouseLimit3(@Param("houseId") Long houseId);


//    @Query(value = "SELECT * FROM review WHERE review_house = :houseId", nativeQuery = true)
//    Page<Review> findReviewsByHouseId(@Param("houseId") Long houseId, Pageable pageable);

    @Query("""
    SELECT r FROM Review r 
    JOIN FETCH r.writer 
    WHERE r.house.id = :houseId 
    ORDER BY r.createdAt DESC
    """)
    Page<Review> findReviewsByHouseId(@Param("houseId") Long houseId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.writer.id = :writerId ORDER BY r.createdAt DESC")
    Page<Review> findReviewsByWriterId(@Param("writerId") Long writerId, Pageable pageable);

    @Query("SELECT COALESCE(AVG(r.score), 0), COUNT(r) " +
            "FROM Review r " +
            "WHERE r.house.id = :houseId")
    Object findAverageScoreAndCountByHouseId(@Param("houseId") Long houseId);
}
