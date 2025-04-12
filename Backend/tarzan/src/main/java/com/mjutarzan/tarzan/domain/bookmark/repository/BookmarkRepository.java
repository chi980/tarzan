package com.mjutarzan.tarzan.domain.bookmark.repository;

import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("""
    SELECT 
    b
    FROM Bookmark b
    JOIN b.house h
    WHERE b.user.id = :userId 
    AND b.status = :status
""")
    Page<Bookmark> findAllBookmarksByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") BookmarkStatus status,
            Pageable pageable
    );

    @Query("""
    SELECT 
    b
    FROM Bookmark b
    JOIN b.house h
    WHERE b.user.id = :userId 
""")
    Page<Bookmark> findByUserId(@Param("userId") Long userId,
                                Pageable pageable);
}
