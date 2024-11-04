package com.mjutarzan.tarzan.domain.fraud.service;

import com.mjutarzan.tarzan.domain.fraud.api.request.PriceRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.PriceListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;

import java.io.IOException;
import java.util.Map;

public interface FraudService {
    RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException;

    Map<String, PriceListResponseDto> getPrice(PriceRequestDto priceRequestDto) throws IOException;
}
