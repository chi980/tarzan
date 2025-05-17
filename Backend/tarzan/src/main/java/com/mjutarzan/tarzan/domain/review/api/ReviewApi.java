package com.mjutarzan.tarzan.domain.review.api;

import com.mjutarzan.tarzan.domain.review.api.request.ReviewListRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewSummaryRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.UpdateReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewSummaryResponseDto;
import com.mjutarzan.tarzan.domain.review.service.ReviewService;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
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
public class ReviewApi {

    private final ReviewService reviewService;
    @GetMapping("/reviews")
    public ResponseEntity<Object> getReviews(ReviewListRequestDto reviewListRequestDto, @AuthenticationPrincipal CustomUserDetails userDto){
        ReviewListResponseDto result = reviewService.getReviews(reviewListRequestDto, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }

    @GetMapping("/reviews/summary")
    public ResponseEntity<?> getReviewSummary(ReviewSummaryRequestDto reviewSummaryRequestDto, @AuthenticationPrincipal CustomUserDetails userDto){
        log.info("getReviewSummary의 house_id: {}", reviewSummaryRequestDto.getHouseId());
        ReviewSummaryResponseDto result = reviewService.getReviewSummary(reviewSummaryRequestDto, userDto);

        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(result)
                        .build()
        );

    }


    @GetMapping("/reviews/{reviewIdx}")
    public ResponseEntity<Object> getReview(@PathVariable Long reviewIdx, @AuthenticationPrincipal CustomUserDetails userDto){

        ReviewListItemResponseDto result = reviewService.getReview(reviewIdx, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .build());
    }

    @PostMapping("/reviews")
    public ResponseEntity<Object> createReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDto) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }

        reviewService.createReview(reviewRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("리뷰가 성공적으로 등록되었습니다.")
                .build());
    }

    @PutMapping("/reviews/{reviewIdx}")
    public ResponseEntity<Object> updateReview(@PathVariable Long reviewIdx, @RequestBody @Valid UpdateReviewRequestDto updateReviewRequestDto, @AuthenticationPrincipal CustomUserDetails userDto){

        reviewService.updateReview(reviewIdx, updateReviewRequestDto, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("리뷰가 성공적으로 수정되었습니다.")
                .build());
    }

    @DeleteMapping("/reviews/{reviewIdx}")
    public ResponseEntity<Object> deleteReview(@PathVariable Long reviewIdx, @AuthenticationPrincipal CustomUserDetails userDto){

        reviewService.deleteReview(reviewIdx, userDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("리뷰가 성공적으로 삭제되었습니다.")
                .build());
    }
}
