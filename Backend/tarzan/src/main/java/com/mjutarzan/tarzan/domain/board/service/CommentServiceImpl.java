package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.CommentListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.CommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateCommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.CommentListItemResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.CommentListResponseDto;
import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.entity.Comment;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.board.repository.CommentRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createComment(CommentRequestDto requestDto, UserDto loginedUserDto) {
        Board board = boardRepository.findById(requestDto.getBoardIdx()).orElseThrow();
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();

        commentRepository.save(
                Comment.builder()
                        .content(requestDto.getContent())
                        .writer(loginedUser)
                        .board(board)
                        .build()
        );
    }

    @Override
    public void updateComment(Long commentIdx, UpdateCommentRequestDto requestDto, UserDto loginedUserDto) {
        Comment comment = commentRepository.findById(commentIdx).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        if (!comment.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("댓글의 주인만 수정할 수 있습니다.");
        }
        
        comment.update(requestDto.getContent());
    }

    @Override
    public void deleteComment(Long commentIdx, UserDto loginedUserDto) {
        Comment comment = commentRepository.findById(commentIdx).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        if (!comment.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("댓글의 주인만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentListResponseDto getComments(CommentListRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        Page<Comment> commentPage = commentRepository.findByBoard_Id(requestDto.getBoardIdx(), pageable);
        List<CommentListItemResponseDto> list = commentPage
                .stream()
                .map(comment -> {
                    return new CommentListItemResponseDto(comment, comment.getWriter().getEmail().equals(loginedUserDto.getEmail()));
                })
                .collect(Collectors.toList());

        return CommentListResponseDto.builder()
                .count(commentPage.getTotalElements())
                .list(list)
                .build();
    }
}
