package com.mjutarzan.tarzan.domain.review.service;

import com.mjutarzan.tarzan.domain.review.api.request.ReviewListRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.UpdateReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface ReviewService {
    ReviewListResponseDto getReviews(ReviewListRequestDto reviewListRequestDto, CustomUserDetails userDto);

    ReviewListItemResponseDto getReview(Long reviewIdx, CustomUserDetails userDto);

    void createReview(ReviewRequestDto reviewRequestDto, CustomUserDetails userDto);

    void updateReview(Long reviewIdx, UpdateReviewRequestDto updateReviewRequestDto, CustomUserDetails userDto);

    void deleteReview(Long reviewIdx, CustomUserDetails userDto);
}
