package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.house.service.ApiHouseService;
import com.mjutarzan.tarzan.global.common.service.CSVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final CSVService csvService;
    private final ApiHouseService apiHouseService;

    @GetMapping("/api/data/test")
    @ResponseBody
    public String testApi(){
        return "ok";
    }

    @GetMapping("/api/data/house")
    @ResponseBody
    public String dataHouse(){
        String filePath = "static/data/house.csv";
        List<String[]> list = csvService.readCsvFile(filePath);
        apiHouseService.saveApiHouses(list);
        return "ok";
    }



    @GetMapping("/api/data/house")
    @ResponseBody
    public String Amenity(){
        String filePath = "static/data/house.csv";
        List<String[]> list = csvService.readCsvFile(filePath);
        apiHouseService.saveApiHouses(list);
        return "ok";
    }
}