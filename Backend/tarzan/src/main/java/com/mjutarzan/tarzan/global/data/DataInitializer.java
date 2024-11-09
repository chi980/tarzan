package com.mjutarzan.tarzan.global.data;

import com.mjutarzan.tarzan.global.data.service.DataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "tarzan.data-load", havingValue = "true")
public class DataInitializer {

    @Value("${tarzan.data-truncate}")
    private boolean isDataTruncate;

    private final DataService dataService;

//    Bean 초기화 후 한 번 실행되므로 서버 시작 시 한 번만 실행
    @PostConstruct
    public void initData() {
        log.info("data 초기화 시작");

        if(isDataTruncate){
            dataService.deleteAll();
        }

        // house data
        String houseFilePath = "static/data/house.csv";
        dataService.storeHouseList(houseFilePath, 5, 6);

        // civic center data
        String civicCenterFilePath = "static/data/civic_center.csv";
        dataService.storeCivicCenterList(civicCenterFilePath, 4, 5);

        // gym data
        String gymFilePath = "static/data/gym.csv";
        dataService.storeGymList(gymFilePath, 4, 5);

        // park data
        String parkFilePath = "static/data/park.csv";
        dataService.storeParkList(parkFilePath, 4, 5);

        // hospital data
        String hospitalFilePath = "static/data/hospital.csv";
        dataService.storeHospitalList(hospitalFilePath, 3, 4);

        // medical clinic data
        String medicalClinicFilePath = "static/data/medical_clinic.csv";
        dataService.storeMedicalClinicList(medicalClinicFilePath, 3, 4);

        // pharmacy data
        String pharmacyFilePath = "static/data/pharmacy.csv";
        dataService.storePharmacyList(pharmacyFilePath, 3, 4);

        // cctv data
        String cctvFilePath = "static/data/cctv.csv";
        dataService.storeCctvList(cctvFilePath, 2, 3);

        // police data
        String policeFilePath = "static/data/police.csv";
        dataService.storePoliceList(policeFilePath, 6, 7);

        // convenience store data
        String convenienceStoreFilePath = "static/data/convenience_store.csv";
        dataService.storeConvenienceStoreList(convenienceStoreFilePath, 13, 14);

        // mart data
        String martFilePath = "static/data/mart.csv";
        dataService.storeMartList(martFilePath, 8, 9);

        // subway data
        String subwayFilePath = "static/data/subway.csv";
        dataService.storeSubwayList(subwayFilePath, 5, 6);

        // bus data
        String busFilePath = "static/data/bus_stop.csv";
        dataService.storeBusList(busFilePath, 2, 3);

        // bicycle data
        String bicycleFilePath = "static/data/bicycle.csv";
        dataService.storeBicycleList(bicycleFilePath, 4, 5);

        log.info("data 초기화 끝");
    }
}
