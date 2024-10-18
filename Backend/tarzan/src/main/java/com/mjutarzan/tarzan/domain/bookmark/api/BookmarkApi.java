package com.mjutarzan.tarzan.domain.bookmark.api;

import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkListRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithApiHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithUserHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.UpdateBookmarkRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.service.BookmarkService;
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
public class BookmarkApi {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark")
    public ResponseEntity<?> createBookmarkWithApiHouse(@RequestBody BookmarkWithApiHouseRequestDto bookmarkRequestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto){

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }

        bookmarkService.createBookmarkWithApiHouse(bookmarkRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("북마크가 성공적으로 등록되었습니다.")
                .build());
    }

    @PostMapping("/bookmark/user")
    public ResponseEntity<?> createBookmarkWithUserHouse(@RequestBody BookmarkWithUserHouseRequestDto bookmarkRequestDto, BindingResult bindingResult, @AuthenticationPrincipal UserDto userDto){

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }

        bookmarkService.createBookmarkWithUserHouse(bookmarkRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("북마크가 성공적으로 등록되었습니다.")
                .build());
    }

    @GetMapping("/bookmark")
    public ResponseEntity<?> getBookmarks(BookmarkListRequestDto bookmarkListRequestDto, @AuthenticationPrincipal UserDto userDto){

        BookmarkListResponseDto result = bookmarkService.getBookmarks(bookmarkListRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());

    }

    @GetMapping("/bookmark/{bookmarkIdx}")
    public ResponseEntity<?> getBookmark(@PathVariable Long bookmarkIdx, @AuthenticationPrincipal UserDto userDto){

        BookmarkDetailResponseDto result = bookmarkService.getBookmark(bookmarkIdx, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());

    }

    @PutMapping("/bookmark/{bookmarkIdx}")
    public ResponseEntity<?> updateBookmark(@PathVariable Long bookmarkIdx, @RequestBody @Valid UpdateBookmarkRequestDto updateBookmarkRequestDto, @AuthenticationPrincipal UserDto userDto){

        bookmarkService.updateBookmark(bookmarkIdx, updateBookmarkRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("북마크가 성공적으로 수정되었습니다.")
                .build());

    }

    @DeleteMapping("/bookmark/{bookmarkIdx}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long bookmarkIdx, @AuthenticationPrincipal UserDto userDto){

        bookmarkService.deleteBookmark(bookmarkIdx, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("북마크가 성공적으로 삭제되었습니다.")
                .build());

    }

}
