package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.api.request.ApiHouseListRequestDto;
import com.mjutarzan.tarzan.domain.house.api.request.HouseSearchRequestDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseItemResposeDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.SimpleHouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.model.dto.HouseIndexes;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.repository.ReviewRepository;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiHouseServiceImpl implements ApiHouseService{

    private final LocationService locationService;
    private final ApiHouseRepository apiHouseRepository;
    private final ReviewRepository reviewRepository;
    @Override
    public void saveApiHouses(List<ApiHouse> apiHouseList){
        apiHouseRepository.saveAll(apiHouseList);
    }

    @Override
    public List<SimpleHouseListItemResponseDto> getHouses(ApiHouseListRequestDto requestDto) {
        List<SimpleHouseListItemResponseDto> list = apiHouseRepository.findAllWithinRadius(requestDto.getLongitude(), requestDto.getLatitude(), requestDto.getRadius()).stream()
                .map(apiHouse -> SimpleHouseListItemResponseDto
                        .builder()
                        .id(apiHouse.getId())
                        .latitude(apiHouse.getLocation().getX())
                        .longitude(apiHouse.getLocation().getY())
                        .build())
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public HouseItemResposeDto getHouse(Long houseIdx, CustomUserDetails userDto) {
        ApiHouse house = apiHouseRepository.findById(houseIdx).orElseThrow();

//        index 처리하는 로직 추가

        List<ReviewListItemResponseDto> houseReviewList = reviewRepository.findByHouseLimit3(houseIdx).stream()
                .map(review -> ReviewListItemResponseDto
                        .builder()
                        .id(review.getId())
                        .imgUrl(review.getImgUrl())
                        .score(review.getScore())
                        .leaseType(review.getLeaseType())
                        .deposit(review.getDeposit())
                        .managementFee(review.getManagementFee())
                        .floor(review.getFloor())
                        .advantage(review.getAdvantage())
                        .advantageTagList(review.getAdvantageTagList())
                        .disadvantage(review.getDisadvantage())
                        .disadvantageTagList(review.getDisadvantageTagList())
                        .writerNickname(review.getWriter().getNickname())
                        .isWriter(review.getWriter().getEmail().equals(userDto.getEmail()))
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return HouseItemResposeDto.builder()
                .id(house.getId())
                .name(house.getName())
                .address(house.getAddress())
                .category(house.getCategory())
                .latitude(house.getLocation().getX())
                .longitude(house.getLocation().getY())
                .indexes(HouseIndexes.builder()
                        .indexAmenity(0)
                        .indexClinic(0)
                        .indexSecurity(0)
                        .indexShopping(0)
                        .indexTransportation(0)
                        .build())
                .reviewImageList(houseReviewList.stream().map(review -> review.getImgUrl()).collect(Collectors.toList()))
                .reviewList(houseReviewList)
                .build();
    }

    @Override
    public HouseListResponseDto searchHouses(HouseSearchRequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());

        Page<ApiHouse> apiHousePages = apiHouseRepository.findByNameContaining(requestDto.getSearch(), pageable);
        List<HouseListItemResponseDto> list = apiHousePages
                .stream()
                .map(apiHouse -> HouseListItemResponseDto.builder()
                        .id(apiHouse.getId())
                        .name(apiHouse.getName())
                        .category(apiHouse.getCategory())
                        .address(apiHouse.getAddress())
                        .latitude(apiHouse.getLocation().getX())
                        .longitude(apiHouse.getLocation().getY())
                        .build())
                .collect(Collectors.toList());

        return HouseListResponseDto
                .builder()
                .count(apiHousePages.getTotalElements())
                .list(list)
                .build();
    }

}
