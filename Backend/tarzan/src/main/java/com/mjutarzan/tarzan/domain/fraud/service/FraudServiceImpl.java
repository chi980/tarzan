package com.mjutarzan.tarzan.domain.fraud.service;

import com.mjutarzan.tarzan.domain.fraud.api.request.PriceRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.request.RealEstateRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.*;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.TbLnOpendataRentVResponseWrapper;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.TbLnOpendataSaleVResponseWrapper;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.VWorldRealEstateApiResponse;
import com.mjutarzan.tarzan.global.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
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
    @Value("${api.data-seoul.url}")
    private String dataSeoulUrl;
    @Value("${api.data-seoul.key}")
    private String dataSeoulKey;

    private final RestTemplate restTemplate;

    @Override
    public RealEstateListResponseDto getRealEstate(RealEstateRequestDto realEstateRequestDto) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(vworldRealEstateUrl)
                .queryParam("key", vworldRealEstateKey)
                .queryParam("ldCode", realEstateRequestDto.getGu().getCode())
                .queryParam("sttusSeCode", "1") // 영업중만
                .queryParam("format", "json") // 응답결과 형식
                .queryParam("numOfRows", realEstateRequestDto.getNumOfRows().toString()) // 검색건수
                .queryParam("pageNo", realEstateRequestDto.getPageNo().toString()); // 페이지번호

        String uri = builder.toUriString();

        String searchBy = realEstateRequestDto.getSearchBy();
        String search = realEstateRequestDto.getSearch();
        if ("중개업자명".equals(searchBy)) {
            uri += ("&brkrNm="+search);
        } else if ("사업자상호".equals(searchBy)) {
            uri += ("&bsnmCmpnm="+search);
        }

        log.info("uri: {}", uri);
        String rawresponse = restTemplate.getForObject(uri, String.class);
        log.info("rawresponse: {}", rawresponse);
        VWorldRealEstateApiResponse response = restTemplate.getForObject(uri, VWorldRealEstateApiResponse.class);
        log.info("res: {}", response);

        if(response.getEdOffices() != null){
            List<RealEstateListItemResponseDto> result = response.getEdOffices().getField().stream()
                    .map(RealEstateListItemResponseDto::getInstance)
                    .collect(Collectors.toList());

            return RealEstateListResponseDto.builder()
                    .count(response.getEdOffices().getTotalCount())
                    .list(result)
                    .isNext(response.getEdOffices().getTotalCount() > response.getEdOffices().getNumOfRows() * response.getEdOffices().getPageNo())
                    .build();
        }else{
            throw new ResourceNotFoundException("자료가 없습니다.");
        }
    }

    @Override
    /**
     * 부동산 전월세
     * @param priceRequestDto
     * @return
     */
    public PriceListResponseDto getRentPrice(PriceRequestDto priceRequestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataSeoulUrl)
                .pathSegment(dataSeoulKey, "json", "tbLnOpendataRentV",
                        String.valueOf(priceRequestDto.getPageNo()),
                        String.valueOf(priceRequestDto.getPageNo() + priceRequestDto.getNumOfRows() - 1))
                .path("/ /"+priceRequestDto.getGu().getCode()+"/ /"+priceRequestDto.getDong()+"/ "); // 접수연도, 지번구분, 지번구분명 고려X


        String search = priceRequestDto.getSearch();
        String searchBy = priceRequestDto.getSearchBy();

        if("지번".equals(searchBy)){
            String[] streetNumber = search.split("-");
            if(streetNumber.length!=2) throw new IllegalArgumentException("지번 형식이 잘못되었습니다.");

            builder.pathSegment(streetNumber[0], streetNumber[1]);
        }else if("건물명".equals(searchBy)){
            builder.path("/ / / ").pathSegment(search);
        }
        builder.encode();

        String uri = builder.toUriString();
        log.info("url: {}", uri);
        TbLnOpendataRentVResponseWrapper response = restTemplate.getForObject(uri, TbLnOpendataRentVResponseWrapper.class);
        log.info("res: {}", response);

        if(response.getTbLnOpendataRentV() != null){
            List<RentListItemResponseDto> list = response.getTbLnOpendataRentV().getRows().stream()
                    .map(RentListItemResponseDto::getInstance)
                    .collect(Collectors.toList());
            return PriceListResponseDto.builder()
                    .count(response.getTbLnOpendataRentV().getListTotalCount())
                    .list(list)
                    .isNext(response.getTbLnOpendataRentV().getListTotalCount() > priceRequestDto.getNumOfRows() * priceRequestDto.getPageNo())
                    .build();

        }else{
            throw new ResourceNotFoundException("자료가 없습니다.");
        }

    }

    @Override
    /**
     * 부동산 실거래가
     * @param priceRequestDto
     * @return
     */
    public PriceListResponseDto getSalePrice(PriceRequestDto priceRequestDto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataSeoulUrl)
                .pathSegment(dataSeoulKey, "json", "tbLnOpendataRtmsV",
                        String.valueOf(priceRequestDto.getPageNo()),
                        String.valueOf(priceRequestDto.getPageNo() + priceRequestDto.getNumOfRows() - 1))
                .path("/ /"+priceRequestDto.getGu().getCode()+"/ /"+priceRequestDto.getDong()+"/ / "); // 접수연도, 지번구분, 지번구분명 고려X

        String search = priceRequestDto.getSearch();
        String searchBy = priceRequestDto.getSearchBy();

        if("지번".equals(searchBy)){
            String[] streetNumber = search.split("-");
            if(streetNumber.length!=2) throw new IllegalArgumentException("지번 형식이 잘못되었습니다.");

            builder.pathSegment(streetNumber[0], streetNumber[1]);
        }else if("건물명".equals(searchBy)){
            builder.path("/ / ").pathSegment(search);
        }
        builder.encode();

        String uri = builder.toUriString();
        log.info("url: {}", uri);
        TbLnOpendataSaleVResponseWrapper response = restTemplate.getForObject(uri, TbLnOpendataSaleVResponseWrapper.class);
        log.info("res: {}", response);
        if(response.getTbLnOpendataSaleV() != null) {
            List<SaleListItemResponseDto> list = response.getTbLnOpendataSaleV().getRows().stream()
                    .map(SaleListItemResponseDto::getInstance)
                    .collect(Collectors.toList());

            return PriceListResponseDto.builder()
                    .count(response.getTbLnOpendataSaleV().getListTotalCount())
                    .list(list)
                    .isNext(response.getTbLnOpendataSaleV().getListTotalCount() > priceRequestDto.getNumOfRows() * priceRequestDto.getPageNo())
                    .build();
        }else{
            throw new ResourceNotFoundException("자료가 없습니다.");
        }
    }


}
