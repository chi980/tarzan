package com.mjutarzan.tarzan.domain.house.api;

import com.mjutarzan.tarzan.domain.house.api.dto.response.HouseDto;
import com.mjutarzan.tarzan.domain.house.service.HouseService;
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
public class HouseApi {
    private final HouseService houseService;

    @GetMapping("/house")
    public ResponseEntity<?> getHouseList(){
        List<HouseDto> houseList = houseService.findAll();

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                        .success(true)
                        .data(houseList)
                .build());
    }
}
