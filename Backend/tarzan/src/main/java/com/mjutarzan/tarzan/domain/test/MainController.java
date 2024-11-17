package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.house.service.ApiHouseService;
import com.mjutarzan.tarzan.global.data.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final DataService dataService;
    private final ApiHouseService apiHouseService;

    @GetMapping("/api/test")
    @ResponseBody
    public String testApi(){
        return "ok";
    }

    @GetMapping("/api/data/house")
    @ResponseBody
    public String storeHouseList(){
        String filePath = "static/data/house.csv";
        dataService.storeHouseList(filePath, 5, 6);
        return "ok";
    }



    @GetMapping("/api/data/civic-center")
    @ResponseBody
    public String storeCivicCenterList(){
        String filePath = "static/data/civic_center.csv";
        dataService.storeCivicCenterList(filePath, 4, 5);

        return "ok";
    }

    @GetMapping("/api/data/gym")
    @ResponseBody
    public String storeGymList(){
        String filePath = "static/data/gym.csv";
        dataService.storeGymList(filePath, 4, 5);
        return "ok";
    }

    @GetMapping("/api/data/park")
    @ResponseBody
    public String storeParkList(){
        String filePath = "static/data/park.csv";
        dataService.storeParkList(filePath, 4, 5);
        return "ok";
    }

    @GetMapping("/api/data/hospital")
    @ResponseBody
    public String storeHospitalList(){
        String filePath = "static/data/hospital.csv";
        dataService.storeHospitalList(filePath, 3, 4);
        return "ok";
    }

    @GetMapping("/api/data/medical-clinic")
    @ResponseBody
    public String storeMedicalClinicList(){
        String filePath = "static/data/medical_clinic.csv";
        dataService.storeMedicalClinicList(filePath, 3, 4);
        return "ok";
    }

    @GetMapping("/api/data/pharmacy")
    @ResponseBody
    public String storePharmacyList(){
        String filePath = "static/data/pharmacy.csv";
        dataService.storePharmacyList(filePath, 3, 4);
        return "ok";
    }

    @GetMapping("/api/data/cctv")
    @ResponseBody
    public String storeCctvList(){
        String filePath = "static/data/cctv.csv";
        dataService.storeCctvList(filePath, 2, 3);
        return "ok";
    }

    @GetMapping("/api/data/police")
    @ResponseBody
    public String storePoliceList(){
        String filePath = "static/data/police.csv";
        dataService.storePoliceList(filePath, 6, 7);
        return "ok";
    }

    @GetMapping("/api/data/convenience-store")
    @ResponseBody
    public String storeConvenienceStoreList(){
        String filePath = "static/data/convenience_store.csv";
        dataService.storeConvenienceStoreList(filePath, 13, 14);
        return "ok";
    }
    @GetMapping("/api/data/mart")
    @ResponseBody
    public String storeMartList(){
        String filePath = "static/data/mart.csv";
        dataService.storeMartList(filePath, 8, 9);
        return "ok";
    }
    @GetMapping("/api/data/subway")
    @ResponseBody
    public String storeSubwayList(){
        String filePath = "static/data/subway.csv";
        dataService.storeSubwayList(filePath, 5, 6);
        return "ok";
    }

    @GetMapping("/api/data/bus")
    @ResponseBody
    public String storeBusList(){
        String filePath = "static/data/bus_stop.csv";
        dataService.storeBusList(filePath, 2, 3);
        return "ok";
    }
//수정 필
    @GetMapping("/api/data/bicycle")
    @ResponseBody
    public String storeBicycleList(){
        String filePath = "static/data/bicycle.csv";
        dataService.storeBicycleList(filePath, 4, 5);
        return "ok";
    }

    @GetMapping("/api/data/all")
    @ResponseBody
    public String storeAllData() {
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

        return "ok";
    }

}