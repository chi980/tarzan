package com.mjutarzan.tarzan.domain.review.service;

import com.mjutarzan.tarzan.domain.house.entity.House;
import com.mjutarzan.tarzan.domain.house.repository.HouseRepository;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewListRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewSummaryRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.UpdateReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewSummaryResponseDto;
import com.mjutarzan.tarzan.domain.review.entity.Review;
import com.mjutarzan.tarzan.domain.review.repository.ReviewRepository;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UserReviewRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import jakarta.persistence.Entity;
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
public class ReviewServiceImpl implements ReviewService{

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewListResponseDto getReviews(ReviewListRequestDto requestDto, CustomUserDetails loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        Page<Review> reviewPages = reviewRepository.findReviewsByHouseId(requestDto.getHouseIdx(), pageable);

        List<ReviewListItemResponseDto> list =reviewPages.getContent().stream()
                        .map(review -> new ReviewListItemResponseDto(review, review.getWriter().getEmail().equals(loginedUserDto.getEmail())))
                                .collect(Collectors.toList());

        return ReviewListResponseDto.builder()
                .count(reviewPages.getTotalElements())
                .list(list)
                .isNext(reviewPages.hasNext())
                .build();
    }

    @Override
    public ReviewListItemResponseDto getReview(Long reviewId, CustomUserDetails loginedUserDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        return new ReviewListItemResponseDto(review, review.getWriter().getEmail().equals(loginedUserDto.getEmail()));
    }
    

    @Override
    public void createReview(ReviewRequestDto requestDto, CustomUserDetails loginedUserDto) {
        House house = houseRepository.findById(requestDto.getHouseId()).orElseThrow();
        log.info("house id: {}", house.getId());
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        log.info("logined user: {}", loginedUser.getId());
        reviewRepository.save(Review.builder()
                .score(requestDto.getScore())
                .leaseType(requestDto.getLeaseType())
                .rent(requestDto.getRent())
                .deposit(requestDto.getDeposit())
                .managementFee(requestDto.getManagementFee())
                .residencePeriod(requestDto.getResidencePeriod())
                .floor(requestDto.getFloor())
                .advantage(requestDto.getAdvantage())
                .disadvantage(requestDto.getDisadvantage())
                .advantageTagList(requestDto.getAdvantageTagList())
                .disadvantageTagList(requestDto.getDisadvantageTagList())
                .house(house)
                .writer(loginedUser)
                .build());
    }

    @Override
    public void updateReview(Long reviewId, UpdateReviewRequestDto requestDto, CustomUserDetails loginedUserDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!review.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("리뷰 주인만 수정할 수 있습니다.");
        }

        review.updateReview(requestDto);
    }

    @Override
    public void deleteReview(Long reviewId, CustomUserDetails loginedUserDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!review.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("리뷰 주인만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }

    @Override
    public ReviewListResponseDto getReviews(UserReviewRequestDto requestDto, CustomUserDetails loginedUserDto)throws EntityNotFoundException {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("없는 사용자입니다."));


        Page<Review> reviewPages = reviewRepository.findReviewsByWriterId(loginedUser.getId(), pageable);

        List<ReviewListItemResponseDto> list =reviewPages.getContent().stream()
                .map(review -> new ReviewListItemResponseDto(review, review.getWriter().getEmail().equals(loginedUserDto.getEmail())))
                .collect(Collectors.toList());

        return ReviewListResponseDto.builder()
                .count(reviewPages.getTotalElements())
                .list(list)
                .isNext(reviewPages.hasNext())
                .build();
    }

    @Override
    public ReviewSummaryResponseDto getReviewSummary(ReviewSummaryRequestDto requestDto, CustomUserDetails userDto) {
        Object[] result = (Object[]) reviewRepository.findAverageScoreAndCountByHouseId(requestDto.getHouseId());
        Double avgScore = ((Number) result[0]).doubleValue();
        Long count = ((Number) result[1]).longValue();



        return ReviewSummaryResponseDto.builder()
                .count(count)
                .score(avgScore)
                .build();
    }
}
