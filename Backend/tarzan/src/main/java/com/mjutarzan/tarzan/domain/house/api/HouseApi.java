package com.mjutarzan.tarzan.domain.house.api;

import com.mjutarzan.tarzan.domain.house.api.request.ApiHouseListRequestDto;
import com.mjutarzan.tarzan.domain.house.api.request.HouseSearchRequestDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseItemResposeDto;
import com.mjutarzan.tarzan.domain.house.api.response.HouseListResponseDto;
import com.mjutarzan.tarzan.domain.house.api.response.SimpleHouseListItemResponseDto;
import com.mjutarzan.tarzan.domain.house.service.ApiHouseService;
import com.mjutarzan.tarzan.domain.house.service.UserHouseService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HouseApi {
    private final ApiHouseService apiHouseService;
    private final UserHouseService userHouseService;

    @GetMapping("/houses")
    public ResponseEntity<?> getHouses(ApiHouseListRequestDto apiHouseListRequestDto) {

        List<SimpleHouseListItemResponseDto> list = apiHouseService.getHouses(apiHouseListRequestDto);

        return ResponseEntity.ok().body(
                BaseResponseDto
                        .builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(list)
                        .build());
    }

    @GetMapping("/houses/{houseIdx}")
    public ResponseEntity<?> getHouse(@PathVariable Long houseIdx) {

        HouseItemResposeDto response = apiHouseService.getHouse(houseIdx);

        return ResponseEntity.ok().body(
                BaseResponseDto
                        .builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(response)
                        .build());
    }

    @GetMapping("/houses/search")
    public ResponseEntity<?> searchHouses(HouseSearchRequestDto houseSearchRequestDto) {
        HouseListResponseDto result = apiHouseService.searchHouses(houseSearchRequestDto);
        return ResponseEntity.ok().body(
                BaseResponseDto
                        .builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .build());
    }
}
