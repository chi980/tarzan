package com.mjutarzan.tarzan.global.loader.service;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CSVService {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    private final ResourceLoader resourceLoader;
    private final GeometryFactory geometryFactory;
    private final ApiHouseRepository apiHouseRepository;

    @PostConstruct
    public void init() {
        if(ddlAuto.contains("create")){
            try{
                log.info("csv 파일을 자동으로 업로드합니다.");
//                loadHouseCsvWithoutBatch();
                loadHouseCsv();

            }catch(Exception e){
                log.error("csv 파일 업로드 중 오류가 발생했습니다.", e);
            }
        }else{
            log.info("이미 csv 파일이 업로드 되어 있습니다.");
        }
    }

    private void loadHouseCsv() {
        long startTime = System.currentTimeMillis();



        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("CSV 파일 로드 및 처리 시간: {} 밀리초", elapsedTime);
    }

    private void loadHouseCsvWithoutBatch() throws Exception{
        long startTime = System.currentTimeMillis();

        Resource resource = resourceLoader.getResource("classpath:/static/data/house.csv");
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            String[] headers = reader.readNext(); // 헤더 라인을 읽어옴
            List<ApiHouse> apiHouses = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                String address = line[0];
                String name = line[1];
                String category = line[2];
                Double latitude = Double.valueOf(line[5]);
                Double longitude = Double.valueOf(line[6]);
                Point location = geometryFactory.createPoint(new Coordinate(latitude, longitude));

                ApiHouse apiHouse = ApiHouse.builder()
                        .address(address)
                        .name(name)
                        .category(category)
                        .location(location)
                        .build();
                apiHouses.add(apiHouse);
            }
            apiHouseRepository.saveAll(apiHouses);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("CSV 파일 로드 및 처리 시간: {} 밀리초", elapsedTime);
    }

}
