package com.mjutarzan.tarzan.domain.bookmark.api;

import com.mjutarzan.tarzan.domain.bookmark.api.request.*;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.CompareBookmarkResponseDto;
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
//원룸의 비교와 점수 매기기를 위해 각 항목을 어떻게 점수화할지 기준을 세우면 좋습니다. 예를 들어, 각 항목의 가중치를 설정하고 해당 항목의 값을 기준으로 점수를 계산하는 방식입니다. 아래는 제안하는 방식입니다:
//
//1. **대중교통 수 (house_index_transportation)**:
//   - 반경 5km 내에 있는 대중교통의 수에 따라 점수를 매깁니다. 예를 들어, 0~2개: 10점, 3~5개: 20점, 6개 이상: 30점 등으로 설정할 수 있습니다.
//
//2. **체크리스트 수 (house_water)**:
//   - 체크리스트에서 `true`가 많은 만큼 점수를 부여합니다. 예를 들어, `true`가 80% 이상일 경우 30점, 60~80%는 20점, 그 이하일 경우 10점.
//
//3. **편의성 지표 (house_index_commerce, house_index_convenience 등)**:
//   - 각 항목별로 지표 점수를 설정하고, 해당 점수를 직접 반영하거나, 점수 범위에 따라 가중치를 부여할 수 있습니다.
//
//4. **안전성 (house_index_safety)**:
//   - 안전 지수에 따라서 점수를 차등화합니다. 예를 들어, 1~2점: 10점, 3~4점: 20점, 5점: 30점.
//
//5. **위생, 창문, 화장실 등 내부 상태**:
//   - 각 내부 항목에 대한 가중치를 설정합니다. 중요한 항목일수록 가중치를 높이고, 그에 따른 점수를 배정할 수 있습니다.
//
//각 항목별 가중치와 기준을 설정한 후, 이를 합산하여 최종 점수를 매기는 방식이 효율적일 수 있습니다.
    @GetMapping("/bookmark/compare")
    public ResponseEntity<?> compareBookmarks(CompareBookmarksRequestDto compareBookmarksRequestDto, @AuthenticationPrincipal UserDto userDto){

        CompareBookmarkResponseDto result = bookmarkService.compareBookmarks(compareBookmarksRequestDto, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }

}
