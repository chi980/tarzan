package com.mjutarzan.tarzan.domain.fraud.service;

import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;

import java.io.IOException;

public interface FraudService {
    RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException;
}
