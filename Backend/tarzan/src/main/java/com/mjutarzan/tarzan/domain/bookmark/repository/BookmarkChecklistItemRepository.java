package com.mjutarzan.tarzan.domain.bookmark.repository;

import com.mjutarzan.tarzan.domain.bookmark.entity.BookmarkChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkChecklistItemRepository extends JpaRepository<BookmarkChecklistItem, Long> {
}
