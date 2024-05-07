package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.api.dto.response.HouseDto;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HouseServiceImpl implements HouseService{

    private final ApiHouseRepository apiHouseRepository;
    @Override
    public List<HouseDto> findAll() {
        List<HouseDto> houseList = apiHouseRepository.findAll().stream()
                .map(apiHouse -> HouseDto.builder()
                        .houseId(apiHouse.getId())
                        .houseName(apiHouse.getName())
                        .build())
                .collect(Collectors.toList());
        return houseList;
    }
}
