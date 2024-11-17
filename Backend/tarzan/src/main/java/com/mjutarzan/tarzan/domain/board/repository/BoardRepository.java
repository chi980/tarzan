package com.mjutarzan.tarzan.domain.board.repository;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByGu(SiGunGu gu, Pageable pageable);
    Page<Board> findByGuAndTag(SiGunGu gu, BoardTag tag, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Board> findByTitleContaining(@Param("search") String search, Pageable pageable);

    Page<Board> findByWriter(User writer, Pageable pageable);

    List<Board> findByWriter(User writer);
}
