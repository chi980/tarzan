package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDTO;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createBoard(BoardRequestDTO boardRequestDTO, UserDto userDto) {
        User loginedUser =  userRepository.findByNickname(userDto.getNickname()).orElseThrow();
        boardRepository.save(Board.builder()
                        .title(boardRequestDTO.getPostTitle())
                        .content(boardRequestDTO.getContent())
                        .tag(BoardTag.ofKor(boardRequestDTO.getPostTag()))
                        .writer(loginedUser)
                .build());
    }
}
