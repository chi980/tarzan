package com.mjutarzan.tarzan.global.loader.service;

import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.domain.map.entity.amenity.Amenity;
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

import java.io.InputStreamReader;
import java.util.ArrayList;
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
        if (ddlAuto.contains("create")) {
            try {
                log.info("CSV 파일을 자동으로 업로드합니다.");
                loadCsv();  // 메서드 이름을 loadCsv로 변경
            } catch (Exception e) {
                log.error("CSV 파일 업로드 중 오류가 발생했습니다.", e);
            }
        } else {
            log.info("이미 CSV 파일이 업로드 되어 있습니다.");
        }
    }

    private void loadCsv() throws Exception {
        long startTime = System.currentTimeMillis();
        Resource resource = resourceLoader.getResource("classpath:/static/data/map/transaction/bicycle.csv");

        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            String[] headers = reader.readNext(); // 헤더를 읽어옴
            if (headers == null) {
                log.error("CSV 파일에 헤더가 없습니다.");
                return;
            }

            List<Amenity> amenity = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    Double uniqueNumber = Double.parseDouble(line[0]);
                    String name = line[1];
                    String sigungu = line[2];
                    String address = line[3];
                    Double latitude = Double.valueOf(line[5]);
                    Double longitude = Double.valueOf(line[6]);
                    Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude)); // 일반적으로 (경도, 위도) 순서

//                    Amenity amenity1 = Amenity.builder()
//                            .name(name)
//                            .sigungu(sigungu)
//                            .address(address)
//                            .location(location)
//                            .build();
//                    amenity.add(amenity1);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    log.warn("CSV 파일의 데이터 형식이 올바르지 않거나 필드가 누락되었습니다. {}", e.getMessage());
                }
            }

//            apiHouseRepository.saveAll(amenity);
        } catch (Exception e) {
            log.error("CSV 파일 처리 중 오류가 발생했습니다.", e);
            throw e;
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("CSV 파일 로드 및 처리 시간: {} 밀리초", elapsedTime);
    }
}