package com.mjutarzan.tarzan.domain.board.api;

import com.mjutarzan.tarzan.domain.board.api.request.CommentListRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.CommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.request.UpdateCommentRequestDto;
import com.mjutarzan.tarzan.domain.board.api.response.CommentListResponseDto;
import com.mjutarzan.tarzan.domain.board.service.CommentService;
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
public class CommentApi {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<Object> getComments(@ModelAttribute CommentListRequestDto commentListRequestDto, @AuthenticationPrincipal UserDto userDto){
        CommentListResponseDto result = commentService.getComments(commentListRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(result)
                .build());
    }

    @PostMapping("/comments")
    public ResponseEntity<Object> createComment(@RequestBody @Valid CommentRequestDto commentRequestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }

        commentService.createComment(commentRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("댓글이 성공적으로 등록되었습니다.")
                .build());
    }

    @PutMapping("/comments/{commentIdx}")
    public ResponseEntity<Object> updateComment(@PathVariable Long commentIdx, @RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto, @AuthenticationPrincipal UserDto userDto){

        commentService.updateComment(commentIdx, updateCommentRequestDto, userDto);

        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("댓글이 성공적으로 수정되었습니다.")
                        .build());
    }

    @DeleteMapping("/comments/{commentIdx}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentIdx, @AuthenticationPrincipal UserDto userDto){

        commentService.deleteComment(commentIdx, userDto);

        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("댓글이 성공적으로 삭제되었습니다.")
                        .build());
    }
}
