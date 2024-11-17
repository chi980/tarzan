package com.mjutarzan.tarzan.domain.fraud.service;

import com.mjutarzan.tarzan.domain.fraud.api.request.PriceRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.PriceListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;

import java.io.IOException;

public interface FraudService {
    RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException;
    PriceListResponseDto getRentPrice(PriceRequestDto priceRequestDto);
    PriceListResponseDto getSalePrice(PriceRequestDto priceRequestDto);
}
