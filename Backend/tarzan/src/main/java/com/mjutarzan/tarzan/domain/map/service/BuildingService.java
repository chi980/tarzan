package com.mjutarzan.tarzan.domain.map.service;

import com.mjutarzan.tarzan.domain.map.api.request.BuildingRequestDto;
import com.mjutarzan.tarzan.domain.map.api.response.BuildingListItemResponseDto;

import java.util.List;

public interface BuildingService {
    List<BuildingListItemResponseDto> getBuildings(BuildingRequestDto buildingListRequestDto);
}
