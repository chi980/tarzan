package com.mjutarzan.tarzan.domain.board.service;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardSearchRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateBoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardDetailResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListItemResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.board.repository.BoardRepository;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UserBoardRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.common.exception.RequiredParameterMissingException;
import jakarta.persistence.EntityNotFoundException;
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
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createBoard(BoardRequestDto boardDto, UserDto loginedUserDto) {
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        boardRepository.save(Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .tag(boardDto.getTag())
                .gu(boardDto.getGu())
                .writer(loginedUser)
                .build());
    }

    @Override
    public void deleteBoard(Long boardIdx, UserDto loginedUserDto) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!board.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("게시글의 주인만 삭제할 수 있습니다.");
        }

        boardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardListResponseDto getBoards(BoardListRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        if (requestDto.getGu() != null) {
            Page<Board> boardPages = null;
            String tag = requestDto.getTag().replace(" ","").toUpperCase();
            if(tag.equals("ALL")){
                boardPages = boardRepository.findByGu(requestDto.getGu(), pageable);
            }else {
                boardPages = boardRepository.findByGuAndTag(requestDto.getGu(), BoardTag.valueOf(tag), pageable);
            }
            List<BoardListItemResponseDto> list = boardPages
                    .stream()
                    .map(board -> {
                        return new BoardListItemResponseDto(board, board.getWriter().getEmail().equals(loginedUserDto.getEmail()));
                    })
                    .collect(Collectors.toList());

            return BoardListResponseDto.builder()
                    .count(boardPages.getTotalElements())
                    .list(list)
                    .build();

        } else {
            throw new RequiredParameterMissingException("구를 포함해야 합니다.");
        }
    }

    @Override
    public BoardListResponseDto getBoards(UserBoardRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();

        Page<Board> boardPages = boardRepository.findByWriter(loginedUser, pageable);
        List<BoardListItemResponseDto> list = boardPages
                .stream()
                .map(board -> {
                    return new BoardListItemResponseDto(board, true);
                }).collect(Collectors.toList());

        return BoardListResponseDto.builder()
                .count(boardPages.getTotalElements())
                .list(list)
                .build();
    }

    @Override
    public BoardListResponseDto searchBoard(BoardSearchRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        Page<Board> boardPages = boardRepository.findByTitleContaining(requestDto.getSearch(), pageable);
        List<BoardListItemResponseDto> list = boardPages
                .stream()
                .map(board -> {
                    return new BoardListItemResponseDto(board, board.getWriter().getEmail().equals(loginedUserDto.getEmail()));
                })
                .collect(Collectors.toList());

        return BoardListResponseDto.builder()
                .count(boardPages.getTotalElements())
                .list(list)
                .build();

    }

    @Override
    public void updateBoard(Long boardIdx, UpdateBoardRequestDto requestDto, UserDto loginedUserDto) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!board.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("게시글의 주인만 삭제할 수 있습니다.");
        }

        board.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getTag());

        boardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardDetailResponseDto getBoard(Long boardIdx, UserDto loginedUserDto) {
        Board board = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        updateBoardReadCount(board);

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();

        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .tag(board.getTag())
                .readCount(board.getReadCount())
                .writerId(board.getWriter().getId())
                .writerNickname(board.getWriter().getNickname())
                .createdAt(board.getCreatedAt())
                .isWriter(board.getWriter().getId() == loginedUser.getId())
                .build();
    }

    private void updateBoardReadCount(Board board) {
        board.updateReadCount();
    }
}
