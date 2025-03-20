package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardSearchRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateBoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardDetailResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UserBoardRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface BoardService {
    void createBoard(BoardRequestDto jwtTokenUtil, CustomUserDetails loginedUserDto);

    void deleteBoard(Long boardIdx, CustomUserDetails userDto);

    BoardListResponseDto getBoards(BoardListRequestDto boardListRequestDto, CustomUserDetails loginedUserDto);

    BoardListResponseDto getBoards(UserBoardRequestDto userBoardRequestDto, CustomUserDetails userDto);

    BoardListResponseDto searchBoard(BoardSearchRequestDto boardSearchRequestDto, CustomUserDetails userDto);

    void updateBoard(Long boardIdx, UpdateBoardRequestDto updateBoardRequestDto, CustomUserDetails userDto);

    BoardDetailResponseDto getBoard(Long boardIdx, CustomUserDetails userDto);
}
