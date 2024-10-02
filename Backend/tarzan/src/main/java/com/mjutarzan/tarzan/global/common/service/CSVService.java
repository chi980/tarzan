package com.mjutarzan.tarzan.global.common.service;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CSVService {
    private final LocationService locationService;

    public List<String[]> readCsvFile(String filePath){
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        if (!classPathResource.exists()) throw new RuntimeException("파일이 존재하지 않습니다.");

        List<String[]> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(classPathResource.getFile()))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public <S extends DataInstance> List<S> GetEntityListFromStringList(List<String[]> list, Class<S> clazz, int latitudeIdx, int longitudeIdx) {
        return (List<S>) list.stream()
                .skip(1) // 첫 번째 항목 건너뛰기
                .map(info -> {
                    try {
                        // 여기서 클래스의 getInstance 메서드를 호출해 S 타입 객체를 생성
                        Double latitude = Double.parseDouble(info[latitudeIdx]);
                        Double longitude = Double.parseDouble(info[longitudeIdx]);
                        Point location = locationService.createPoint(latitude, longitude);

                        // Reflection을 통해 getInstance 호출
                        return clazz.getDeclaredConstructor().newInstance().getInstance(info, location);
                    } catch (Exception e) {
                        log.error("{}", e.getMessage());
                        log.error("오류: {}", Arrays.toString(info));
                        // 예외가 발생하면 null 반환 (처리 방식에 따라 다르게 처리 가능)
                        return null;
                    }
                })
                .filter(Objects::nonNull) // null이 아닌 항목만 필터링
                .collect(Collectors.toList());
    }
}
