package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

import java.util.List;

public interface BoardService {
    void createBoard(BoardRequestDto boardDto, UserDto loginedUserDto);

    void deleteBoard(Long boardIdx, UserDto userDto);

    List<BoardListResponseDto> getBoards(BoardListRequestDto boardListRequestDto);
}
