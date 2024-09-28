package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.exception.RequiredParameterMissingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createBoard(BoardRequestDto boardDto, UserDto loginedUserDto) {
        User loginedUser =  userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        boardRepository.save(Board.builder()
                        .title(boardDto.getBoardTitle())
                        .content(boardDto.getBoardContent())
                        .tag(BoardTag.fromKor(boardDto.getBoardTag()))
                        .gu(boardDto.getGu())
                        .writer(loginedUser)
                .build());
    }

    @Override
    public void deleteBoard(Long boardIdx, UserDto loginedUserDto) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        User loginedUser =  userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!board.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("게시글의 주인만 삭제할 수 있습니다.");
        }

        boardRepository.deleteById(boardIdx);
    }

    @Override
    public List<BoardListResponseDto> getBoards(BoardListRequestDto boardDto) {
        Pageable pageable = PageRequest.of(boardDto.getPage(), boardDto.getPageSize(), boardDto.getSort());

        if(boardDto.getGu() != null){
            return boardRepository.findByGuAndTag(boardDto.getGu(), boardDto.getTag(), pageable)
                    .stream()
                    .map(BoardListResponseDto::new)
                    .collect(Collectors.toList());
        }else{
            throw new RequiredParameterMissingException("구를 포함해야 합니다.");
        }
    }
}
