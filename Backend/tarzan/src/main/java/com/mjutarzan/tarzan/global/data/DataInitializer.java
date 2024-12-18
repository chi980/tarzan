package com.mjutarzan.tarzan.global.data;

import com.mjutarzan.tarzan.global.data.service.DataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "tarzan.data-load", havingValue = "true")
public class DataInitializer {

    @Value("${tarzan.data-truncate}")
    private boolean isDataTruncate;
    @Value("${tarzan.data-load.bicycle}")
    private boolean isDataLoadBicycle;
    @Value("${tarzan.data-load.bus-stop}")
    private boolean isDataLoadBusStop;
    @Value("${tarzan.data-load.cctv}")
    private boolean isDataLoadCctv;
    @Value("${tarzan.data-load.civic-center}")
    private boolean isDataLoadCivicCenter;
    @Value("${tarzan.data-load.convenience-store}")
    private boolean isDataLoadConvenienceStore;
    @Value("${tarzan.data-load.gym}")
    private boolean isDataLoadGym;
    @Value("${tarzan.data-load.hospital}")
    private boolean isDataLoadHospital;
    @Value("${tarzan.data-load.house}")
    private boolean isDataLoadHouse;
    @Value("${tarzan.data-load.mart}")
    private boolean isDataLoadMart;
    @Value("${tarzan.data-load.medical-clinic}")
    private boolean isDataLoadMedicalClinic;
    @Value("${tarzan.data-load.park}")
    private boolean isDataLoadPark;
    @Value("${tarzan.data-load.pharmacy}")
    private boolean isDataLoadPharmacy;
    @Value("${tarzan.data-load.police}")
    private boolean isDataLoadPolice;
    @Value("${tarzan.data-load.subway}")
    private boolean isDataLoadSubway;

    private final DataService dataService;
    @PostConstruct
    public void initData() {
        log.info("data 초기화 시작");

        if (isDataTruncate) {
            dataService.deleteAll();
        }

        initializeData(isDataLoadHouse,"house data", "static/data/house.csv", (filePath) -> dataService.storeHouseList(filePath, 5, 6));
        initializeData(isDataLoadCivicCenter,"civic center data", "static/data/civic_center.csv", (filePath) -> dataService.storeCivicCenterList(filePath, 4, 5));
        initializeData(isDataLoadGym,"gym data", "static/data/gym.csv", (filePath) -> dataService.storeGymList(filePath, 4, 5));
        initializeData(isDataLoadPark,"park data", "static/data/park.csv", (filePath) -> dataService.storeParkList(filePath, 4, 5));
        initializeData(isDataLoadHospital,"hospital data", "static/data/hospital.csv", (filePath) -> dataService.storeHospitalList(filePath, 3, 4));
        initializeData(isDataLoadMedicalClinic,"medical clinic data", "static/data/medical_clinic.csv", (filePath) -> dataService.storeMedicalClinicList(filePath, 3, 4));
        initializeData(isDataLoadPharmacy,"pharmacy data", "static/data/pharmacy.csv", (filePath) -> dataService.storePharmacyList(filePath, 3, 4));
        initializeData(isDataLoadCctv,"cctv data", "static/data/cctv.csv", (filePath) -> dataService.storeCctvList(filePath, 2, 3));
        initializeData(isDataLoadPolice,"police data", "static/data/police.csv", (filePath) -> dataService.storePoliceList(filePath, 6, 7));
        initializeData(isDataLoadConvenienceStore,"convenience store data", "static/data/convenience_store.csv", (filePath) -> dataService.storeConvenienceStoreList(filePath, 13, 14));
        initializeData(isDataLoadMart,"mart data", "static/data/mart.csv", (filePath) -> dataService.storeMartList(filePath, 8, 9));
        initializeData(isDataLoadSubway,"subway data", "static/data/subway.csv", (filePath) -> dataService.storeSubwayList(filePath, 5, 6));
        initializeData(isDataLoadBusStop,"bus data", "static/data/bus_stop.csv", (filePath) -> dataService.storeBusList(filePath, 2, 3));
        initializeData(isDataLoadBicycle,"bicycle data", "static/data/bicycle.csv", (filePath) -> dataService.storeBicycleList(filePath, 4, 5));

        log.info("data 초기화 끝");
    }

    private void initializeData(boolean isDatLoad, String dataName, String filePath, DataProcessor processor) {
        if(!isDatLoad) return;
        try {
            log.info("{} 초기화 중...", dataName);
            processor.process(filePath);
            log.info("{} 초기화 성공", dataName);
        } catch (Exception e) {
            log.error("{} 초기화 실패: {}", dataName, e.getMessage(), e);
        }
    }

    @FunctionalInterface
    interface DataProcessor {
        void process(String filePath) throws Exception;
    }
//    Bean 초기화 후 한 번 실행되므로 서버 시작 시 한 번만 실행
//    @PostConstruct
//    public void initData() {
//        log.info("data 초기화 시작");
//
//        if(isDataTruncate){
//            dataService.deleteAll();
//        }
//
//        // house data
//        String houseFilePath = "static/data/house.csv";
//        dataService.storeHouseList(houseFilePath, 5, 6);
//
//        // civic center data
//        String civicCenterFilePath = "static/data/civic_center.csv";
//        dataService.storeCivicCenterList(civicCenterFilePath, 4, 5);
//
//        // gym data
//        String gymFilePath = "static/data/gym.csv";
//        dataService.storeGymList(gymFilePath, 4, 5);
//
//        // park data
//        String parkFilePath = "static/data/park.csv";
//        dataService.storeParkList(parkFilePath, 4, 5);
//
//        // hospital data
//        String hospitalFilePath = "static/data/hospital.csv";
//        dataService.storeHospitalList(hospitalFilePath, 3, 4);
//
//        // medical clinic data
//        String medicalClinicFilePath = "static/data/medical_clinic.csv";
//        dataService.storeMedicalClinicList(medicalClinicFilePath, 3, 4);
//
//        // pharmacy data
//        String pharmacyFilePath = "static/data/pharmacy.csv";
//        dataService.storePharmacyList(pharmacyFilePath, 3, 4);
//
//        // cctv data
//        String cctvFilePath = "static/data/cctv.csv";
//        dataService.storeCctvList(cctvFilePath, 2, 3);
//
//        // police data
//        String policeFilePath = "static/data/police.csv";
//        dataService.storePoliceList(policeFilePath, 6, 7);
//
//        // convenience store data
//        String convenienceStoreFilePath = "static/data/convenience_store.csv";
//        dataService.storeConvenienceStoreList(convenienceStoreFilePath, 13, 14);
//
//        // mart data
//        String martFilePath = "static/data/mart.csv";
//        dataService.storeMartList(martFilePath, 8, 9);
//
//        // subway data
//        String subwayFilePath = "static/data/subway.csv";
//        dataService.storeSubwayList(subwayFilePath, 5, 6);
//
//        // bus data
//        String busFilePath = "static/data/bus_stop.csv";
//        dataService.storeBusList(busFilePath, 2, 3);
//
//        // bicycle data
//        String bicycleFilePath = "static/data/bicycle.csv";
//        dataService.storeBicycleList(bicycleFilePath, 4, 5);
//
//        log.info("data 초기화 끝");
//    }
}
