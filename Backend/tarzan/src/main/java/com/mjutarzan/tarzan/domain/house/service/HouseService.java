package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.api.dto.response.HouseDto;

import java.util.List;

public interface HouseService {

    List<HouseDto> findAll();
}
