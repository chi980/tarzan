package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.service.ApiHouseService;
import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
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
        List<ApiHouse> apiHouseList = csvService.GetEntityListFromStringList(list, ApiHouse.class, 5, 6);

        for (int i=0;i<4;i++){
            log.info("{}", apiHouseList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", apiHouseList.size());

        return "ok";
    }



    @GetMapping("/api/data/civic-center")
    @ResponseBody
    public String Amenity(){
        String filePath = "static/data/civic_center.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<CivicCenter> civicCenterList = csvService.GetEntityListFromStringList(list, CivicCenter.class, 4, 5);

        for (int i=0;i<4;i++){
            log.info("{}", civicCenterList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", civicCenterList.size());

        return "ok";
    }
}