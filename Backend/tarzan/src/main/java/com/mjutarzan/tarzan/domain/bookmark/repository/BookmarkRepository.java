package com.mjutarzan.tarzan.domain.bookmark.repository;

import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListItemResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query(value = """
    SELECT 
        b.id AS bookmark_id,
        h.id AS bookmark_house_id,
        h.name AS bookmark_house_name,
        h.address AS bookmark_house_address,
        h.category AS bookmark_house_category,
        CASE 
            WHEN h.dtype = 'user' AND h.user_house_register IS NOT NULL AND h.user_house_register = b.bookmark_user THEN TRUE 
            ELSE FALSE 
        END AS bookmark_is_house_register,
        b.created_at AS bookmark_created_at
    FROM 
        bookmark b
    JOIN 
        house h ON b.bookmark_house = h.id
    WHERE 
        b.bookmark_user = :userId AND b.status = :status
    """,
            countQuery = """
    SELECT COUNT(*)
    FROM 
        bookmark b
    JOIN 
        house h ON b.bookmark_house = h.id
    WHERE 
        b.bookmark_user = :userId AND b.status = :status
    """,
            nativeQuery = true)
    Page<BookmarkListItemResponseDto> findAllBookmarksByUserIdAndStatus(@Param("userId") Long userId, @Param("status")BookmarkStatus status, Pageable pageable);

}
