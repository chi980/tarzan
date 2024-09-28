package com.mjutarzan.tarzan.domain.board.repository;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
