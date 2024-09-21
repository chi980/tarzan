package com.mjutarzan.tarzan.domain.board.api;

import com.mjutarzan.tarzan.domain.board.service.BoardService;
import com.mjutarzan.tarzan.domain.board.api.request.BoardRequestDTO;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardApi {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<Object> createBoard(@RequestBody @Valid BoardRequestDTO boardRequestDTO, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto) {
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

}
