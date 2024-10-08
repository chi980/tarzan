package com.mjutarzan.tarzan.domain.house.service;

import com.mjutarzan.tarzan.domain.house.api.request.ApiHouseListRequestDto;
import com.mjutarzan.tarzan.domain.house.api.request.HouseSearchRequestDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseItemResposeDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.SimpleHouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;

import java.util.List;

public interface ApiHouseService {
    public void saveApiHouses(List<ApiHouse> apiHouseList);

    List<SimpleHouseListItemResponseDto> getHouses(ApiHouseListRequestDto apiHouseListRequestDto);

    HouseItemResposeDto getHouse(Long houseIdx);

    HouseListResponseDto searchHouses(HouseSearchRequestDto houseSearchRequestDto);
}
