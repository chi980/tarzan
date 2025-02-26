package com.mjutarzan.tarzan.domain.review.service;

import com.mjutarzan.tarzan.domain.house.entity.House;
import com.mjutarzan.tarzan.domain.house.repository.HouseRepository;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewListRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.ReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.request.UpdateReviewRequestDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListResponseDto;
import com.mjutarzan.tarzan.domain.review.entity.Review;
import com.mjutarzan.tarzan.domain.review.repository.ReviewRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
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
    public ReviewListResponseDto getReviews(ReviewListRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        Page<Review> reviewPages = reviewRepository.findReviewsByHouseId(requestDto.getHouseIdx(), pageable);

        List<ReviewListItemResponseDto> list = reviewPages.stream()
                .map(review -> {
                    return new ReviewListItemResponseDto(review, loginedUserDto!=null?review.getWriter().getEmail().equals(loginedUserDto.getEmail()):false);
                })
                .collect(Collectors.toList());

        return ReviewListResponseDto.builder()
                .count(reviewPages.getTotalElements())
                .list(list)
                .build();
    }

    @Override
    public ReviewListItemResponseDto getReview(Long reviewId, UserDto loginedUserDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        return new ReviewListItemResponseDto(review, review.getWriter().getEmail().equals(loginedUserDto.getEmail()));
    }
    

    @Override
    public void createReview(ReviewRequestDto requestDto, UserDto loginedUserDto) {
        House house = houseRepository.findById(requestDto.getHouseId()).orElseThrow();
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
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
    public void updateReview(Long reviewId, UpdateReviewRequestDto requestDto, UserDto loginedUserDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!review.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("리뷰 주인만 수정할 수 있습니다.");
        }

        review.updateReview(requestDto);
    }

    @Override
    public void deleteReview(Long reviewId, UserDto loginedUserDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        if (!review.getWriter().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("리뷰 주인만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }
}
