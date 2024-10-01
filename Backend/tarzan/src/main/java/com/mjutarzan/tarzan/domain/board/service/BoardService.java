package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardSearchRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateBoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UserBoardRequestDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface BoardService {
    void createBoard(BoardRequestDto jwtTokenUtil, UserDto loginedUserDto);

    void deleteBoard(Long boardIdx, UserDto userDto);

    BoardListResponseDto getBoards(BoardListRequestDto boardListRequestDto, UserDto loginedUserDto);

    BoardListResponseDto getBoards(UserBoardRequestDto userBoardRequestDto, UserDto userDto);

    BoardListResponseDto searchBoard(BoardSearchRequestDto boardSearchRequestDto, UserDto userDto);

    void updateBoard(Long boardIdx, UpdateBoardRequestDto updateBoardRequestDto, UserDto userDto);

}
