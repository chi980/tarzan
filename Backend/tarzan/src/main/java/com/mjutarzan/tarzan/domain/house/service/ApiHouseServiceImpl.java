package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.api.request.ApiHouseListRequestDto;
import com.mjutarzan.tarzan.domain.house.api.request.HouseSearchRequestDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseItemResposeDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.SimpleHouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import com.mjutarzan.tarzan.domain.review.repository.ReviewRepository;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
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
        Point location = locationService.createPoint(requestDto.getLatitude(), requestDto.getLongitude());
//        List<SimpleHouseListItemResponseDto> list = apiHouseRepository.findAllWithinRadius(location, requestDto.getRadius()).stream()
//                .map(apiHouse -> SimpleHouseListItemResponseDto
//                        .builder()
//                        .id(apiHouse.getId())
//                        .latitude(apiHouse.getLocation().getX())
//                        .longitude(apiHouse.getLocation().getY())
//                        .build())
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public HouseItemResposeDto getHouse(Long houseIdx) {
        ApiHouse house = apiHouseRepository.findById(houseIdx).orElseThrow();

//        index 처리하는 로직 추가

        List<ReviewListItemResponseDto> houseReviewList = reviewRepository.findByWriterLimit3(houseIdx).stream()
                .map(review -> ReviewListItemResponseDto
                        .builder()
                        .build())
                .collect(Collectors.toList());

        return HouseItemResposeDto.builder()
                .name(house.getName())
                .address(house.getAddress())
                .latitude(house.getLocation().getX())
                .longitude(house.getLocation().getY())
                .indexAmenity(0)
                .indexClinic(0)
                .indexSecurity(0)
                .indexShopping(0)
                .indexTransportation(0)
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
