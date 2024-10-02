package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApiHouseServiceImpl implements ApiHouseService{

    private final ApiHouseRepository apiHouseRepository;
    private final LocationService locationService;

    @Override
    public void saveApiHouses(List<String[]> list){
        List<ApiHouse> apiHouseList = list.stream()
                .skip(1)
                .map(info -> {
                    try {
                        Double latitude = Double.parseDouble(info[5]);
                        Double longitude = Double.parseDouble(info[6]);
                        Point location = locationService.createPoint(latitude, longitude);
                        return new ApiHouse().getInstance(info, location);
                    } catch (Exception e) {
                        // 오류가 발생한 경우 null 반환
                        log.info("오류: {}", Arrays.toString(info));
                        return null;
                    }
                }).filter(Objects::nonNull) // null이 아닌 항목만 필터링
                .collect(Collectors.toList());

        log.info("바꾸기 전 길이: {}", list.size());
        log.info("바꾸고 난 후 길이: {}", apiHouseList.size());

        apiHouseRepository.saveAll(apiHouseList);
    }

}
