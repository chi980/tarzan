package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDTO;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface BoardService {
    void createBoard(BoardRequestDTO boardDto, UserDto loginedUserDto);

    void deleteBoard(Long boardIdx, UserDto userDto);
}
