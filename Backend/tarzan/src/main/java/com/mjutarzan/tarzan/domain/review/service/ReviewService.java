package com.mjutarzan.tarzan.domain.review.service;

import com.mjutarzan.tarzan.domain.review.api.request.ReviewListRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.UpdateReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListResponseDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface ReviewService {
    ReviewListResponseDto getReviews(ReviewListRequestDto reviewListRequestDto, UserDto userDto);

    ReviewListItemResponseDto getReview(Long reviewIdx, UserDto userDto);

    void createReview(ReviewRequestDto reviewRequestDto, UserDto userDto);

    void updateReview(Long reviewIdx, UpdateReviewRequestDto updateReviewRequestDto, UserDto userDto);

    void deleteReview(Long reviewIdx, UserDto userDto);
}
