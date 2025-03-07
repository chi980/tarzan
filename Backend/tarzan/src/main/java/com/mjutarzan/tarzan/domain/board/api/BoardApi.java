package com.mjutarzan.tarzan.domain.board.api;

import com.mjutarzan.tarzan.domain.board.api.request.BoardListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.BoardSearchRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateBoardRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardDetailResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListItemResponseDto;
import com.mjutarzan.tarzan.domain.board.api.response.BoardListResponseDto;
import com.mjutarzan.tarzan.domain.board.service.BoardService;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardApi {

    private final BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<Object> getBoards(BoardListRequestDto boardListRequestDto,  @AuthenticationPrincipal UserDto userDto){
        BoardListResponseDto result = boardService.getBoards(boardListRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(result)
                .build());
    }
    @GetMapping("/board/search")
    public ResponseEntity<Object> searchBoards(BoardSearchRequestDto boardSearchRequestDto,  @AuthenticationPrincipal UserDto userDto){
        BoardListResponseDto result = boardService.searchBoard(boardSearchRequestDto, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }
    @GetMapping("/board/{boardIdx}")
    public ResponseEntity<Object> getBoard(@PathVariable Long boardIdx, @AuthenticationPrincipal UserDto userDto){
        BoardDetailResponseDto result = boardService.getBoard(boardIdx, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }
    @PostMapping("/board")
    public ResponseEntity<Object> createBoard(@RequestBody @Valid BoardRequestDto boardRequestDTO, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }

        boardService.createBoard(boardRequestDTO, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("게시물이 성공적으로 등록되었습니다.")
                .build());
    }

    @PutMapping("/board/{boardIdx}")
    public ResponseEntity<Object> updateBoard(@PathVariable Long boardIdx, @RequestBody @Valid UpdateBoardRequestDto updateBoardRequestDto, @AuthenticationPrincipal UserDto userDto){

        boardService.updateBoard(boardIdx, updateBoardRequestDto,userDto);

        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("게시물이 성공적으로 수정되었습니다.")
                        .build());
    }

    @DeleteMapping("/board/{boardIdx}")
    public ResponseEntity<Object> deleteBoard(@PathVariable Long boardIdx, @AuthenticationPrincipal UserDto userDto) {
        // 게시글 삭제 로직, 소유자가 일치하지 않으면 예외가 발생
            boardService.deleteBoard(boardIdx, userDto);
        // 삭제 성공 시 200 OK 반환
        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("게시물이 성공적으로 삭제되었습니다.")
                        .build()
        );
    }
}
