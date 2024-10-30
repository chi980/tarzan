package com.mjutarzan.tarzan.domain.fraud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListItemResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.VWorldRealEstateApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudServiceImpl implements FraudService{
    @Value("${api.vworld.real-estate.develop.key}")
    private String vworldRealEstateKey;

    @Override
    public RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.vworld.kr/ned/data/getEBOfficeInfo");
        StringBuilder parameter = new StringBuilder();
        parameter.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + vworldRealEstateKey);
        parameter.append("&" + URLEncoder.encode("ldCode", "UTF-8") + "=" + URLEncoder.encode(realEstateRequestDto.getGu().getCode(), "UTF-8"));
        parameter.append("&" + URLEncoder.encode("sttusSeCode", "UTF-8")+ "="+URLEncoder.encode("1", "UTF-8")); // 영업중만
        parameter.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 응답결과 형식(xml 또는 json) */
        parameter.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(realEstateRequestDto.getNumOfRows().toString(), "UTF-8")); /* 검색건수 (최대 1000) */
        parameter.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(realEstateRequestDto.getPageNo().toString(), "UTF-8")); /* 페이지번호 */

        String searchBy = realEstateRequestDto.getSearchBy();
        String search = realEstateRequestDto.getSearch();
        if ("중개업자명".equals(searchBy)) {
            parameter.append("&" + URLEncoder.encode("brkrNm","UTF-8") + "=" + URLEncoder.encode(search, "UTF-8"));
        } else if ("사업자상호".equals(searchBy)) {
            parameter.append("&" + URLEncoder.encode("bsnmCmpnm","UTF-8") + "=" + URLEncoder.encode(search, "UTF-8"));
        }

        URL url = new URL(urlBuilder.append(parameter).toString());
        log.info("url: {}", url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // JSON 응답을 읽어오기
        StringBuilder response = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }

        log.info("response: {}",response.toString());

        // ObjectMapper로 JSON을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        VWorldRealEstateApiResponse apiResponse = objectMapper.readValue(response.toString(), VWorldRealEstateApiResponse.class);


        List<RealEstateListItemResponseDto> result = apiResponse.getEdOffices().getField().stream()
                .map(RealEstateListItemResponseDto::getInstance)
                .collect(Collectors.toList());

        return RealEstateListResponseDto.builder()
                .count(apiResponse.getEdOffices().getTotalCount())
                .list(result)
                .isNext(apiResponse.getEdOffices().getTotalCount() > apiResponse.getEdOffices().getNumOfRows() * apiResponse.getEdOffices().getPageNo())
                .build();
    }
}
