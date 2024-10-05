package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApiHouseServiceImpl implements ApiHouseService{

    private final ApiHouseRepository apiHouseRepository;
    @Override
    public void saveApiHouses(List<ApiHouse> apiHouseList){
        apiHouseRepository.saveAll(apiHouseList);
    }

}
