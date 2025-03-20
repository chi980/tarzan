package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.CommentListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.CommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateCommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.CommentListResponseDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UserCommentRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface CommentService {
    void createComment(CommentRequestDto commentRequestDto, CustomUserDetails userDto);

    void updateComment(Long commentIdx, UpdateCommentRequestDto updateCommentRequestDto, CustomUserDetails userDto);

    void deleteComment(Long commentIdx, CustomUserDetails userDto);

    CommentListResponseDto getComments(CommentListRequestDto commentListRequestDto, CustomUserDetails userDto);

    CommentListResponseDto getComments(UserCommentRequestDto userCommentRequestDto, CustomUserDetails userDto);
}
