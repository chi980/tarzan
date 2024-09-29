package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.CommentListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.CommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateCommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.CommentListResponseDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface CommentService {
    void createComment(CommentRequestDto commentRequestDto, UserDto userDto);

    void updateComment(Long commentIdx, UpdateCommentRequestDto updateCommentRequestDto, UserDto userDto);

    void deleteComment(Long commentIdx, UserDto userDto);

    CommentListResponseDto getComments(CommentListRequestDto commentListRequestDto);
}
