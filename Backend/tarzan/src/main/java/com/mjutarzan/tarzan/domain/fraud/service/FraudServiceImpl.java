package com.mjutarzan.tarzan.domain.fraud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjutarzan.tarzan.domain.fraud.api.request.PriceRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.PriceListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListItemResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RealEstateListResponseDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.RentListItemResponseDto;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.TbLnOpendataRentVResponseWrapper;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.VWorldRealEstateApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudServiceImpl implements FraudService{

    // 부동산 중개업 url, key from vworld
    @Value("${api.vworld.real-estate.url}")
    private String vworldRealEstateUrl;
    @Value("${api.vworld.real-estate.develop.key}")
    private String vworldRealEstateKey;

    // 서울시 부동산 시세
    @Value("${api.data-seoul.url.rent}")
    private String dataSeoulRentUrl;
    @Value("${api.data-seoul.url.sale}")
    private String dataSeoulSaleUrl;
    @Value("${api.data-seoul.key}")
    private String dataSeoulKey;

    private final RestTemplate restTemplate;

    @Override
    public RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(vworldRealEstateUrl);
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

    @Override
    public Map<String, PriceListResponseDto> getPrice(PriceRequestDto priceRequestDto) throws IOException {
        return Map.of(
                "rent", getRentPrice(priceRequestDto)
//                "sale", getSalePrice(priceRequestDto)
        );
    }

    private PriceListResponseDto getRentPrice(PriceRequestDto priceRequestDto)  throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataSeoulRentUrl)
                .queryParam("KEY", dataSeoulKey)
                .queryParam("TYPE", "json")
                .queryParam("SERVICE", "tbLnOpendataRentV")
                .queryParam("START_INDEX", priceRequestDto.getPageNo())
                .queryParam("END_INDEX", priceRequestDto.getPageNo() + priceRequestDto.getNumOfRows() - 1)
                .queryParam("CGG_CD", priceRequestDto.getGu().getCode())
                .queryParam("STDG_CD", priceRequestDto.getDong());

        String search = priceRequestDto.getSearch();
        String searchBy = priceRequestDto.getSearchBy();

        if("건물명".equals(searchBy)){
            builder.queryParam("BLDG_NM", search);
        }else if("지번".equals(searchBy)){
            String[] streetNumber = search.split("-");
            if(streetNumber.length!=2) throw new IllegalArgumentException("지번 형식이 잘못되었습니다.");

            builder.queryParam("MNO", streetNumber[0]);
            builder.queryParam("SNO", streetNumber[1]);
        }

        String uri = builder.toUriString();
        TbLnOpendataRentVResponseWrapper response = restTemplate.getForObject(uri, TbLnOpendataRentVResponseWrapper.class);

        List<RentListItemResponseDto> list = response.getTbLnOpendataRentV().getRows().stream()
                .map(RentListItemResponseDto::getInstance)
                .collect(Collectors.toList());

        return PriceListResponseDto.builder()
                .count(response.getTbLnOpendataRentV().getListTotalCount())
                .list(list)
                .isNext(response.getTbLnOpendataRentV().getListTotalCount() > priceRequestDto.getNumOfRows() * priceRequestDto.getPageNo())
                .build();
    }

    /**
     * 부동산 실거래가
     * @param priceRequestDto
     * @return
     */
    private PriceListResponseDto getSalePrice(PriceRequestDto priceRequestDto) {
        return null;
    }


}
