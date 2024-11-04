package com.mjutarzan.tarzan.domain.fraud.api;

import com.mjutarzan.tarzan.domain.fraud.api.request.PriceRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.PriceListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.service.FraudService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fraud")
public class FraudApi {

    private final FraudService fraudService;

    @GetMapping("/real-estate")
    public ResponseEntity<?> getRealEstate(RealEstateRequestDto realEstateRequestDto){
        RealEstateListResponseDto result = null;
        try {
            result = fraudService.getRealEstate(realEstateRequestDto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching real estate data", e);
        }

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }

    @GetMapping("/price")
    public ResponseEntity<?> getPrice(PriceRequestDto priceRequestDto){
        Map<String, PriceListResponseDto> result = null;
        try {
            result = fraudService.getPrice(priceRequestDto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching price data", e);
        }

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }
}
