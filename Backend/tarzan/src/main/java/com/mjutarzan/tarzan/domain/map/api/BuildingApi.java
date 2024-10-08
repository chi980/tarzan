package com.mjutarzan.tarzan.domain.map.api;

import com.mjutarzan.tarzan.domain.map.api.request.BuildingRequestDto;
import com.mjutarzan.tarzan.domain.map.api.response.BuildingListItemResponseDto;
import com.mjutarzan.tarzan.domain.map.service.BuildingService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BuildingApi {

    private final BuildingService buildingService;

    @GetMapping("/building")
    public ResponseEntity<Object> getBuildings(BuildingRequestDto buildingListRequestDto) {
        List<BuildingListItemResponseDto> result = buildingService.getBuildings(buildingListRequestDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }
}
