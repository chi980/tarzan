package com.mjutarzan.tarzan.global.common.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


}
