package com.mjutarzan.tarzan.domain.board.repository;

import com.mjutarzan.tarzan.domain.board.entity.Comment;
import com.mjutarzan.tarzan.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    Page<Comment> findByBoard_Id(Long boardId, Pageable pageable);

    Page<Comment> findByWriter(User writer, Pageable pageable);
}
