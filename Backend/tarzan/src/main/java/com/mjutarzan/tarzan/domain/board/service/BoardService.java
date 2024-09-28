package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface BoardService {
    void createBoard(BoardRequestDto boardDto, UserDto loginedUserDto);

    void deleteBoard(Long boardIdx, UserDto userDto);
}
