package com.mjutarzan.tarzan.domain.bookmark.repository;

import com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {

    @Query("SELECT c FROM ChecklistItem c WHERE c.user.id = :userId AND c.type = :type")
    List<ChecklistItem> findByUserIdAndType(
            @Param("userId") Long userId,
            @Param("type") ChecklistType type);

    List<ChecklistItem> findAllByIdIn(List<Long> ids);

}
