package com.mjutarzan.tarzan.domain.review.service;

import com.mjutarzan.tarzan.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
}
