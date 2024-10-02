package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.service.ApiHouseService;
import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
import com.mjutarzan.tarzan.domain.map.entity.amenity.Gym;
import com.mjutarzan.tarzan.domain.map.entity.amenity.Park;
import com.mjutarzan.tarzan.domain.map.entity.clinic.Hospital;
import com.mjutarzan.tarzan.domain.map.entity.clinic.MedicalClinic;
import com.mjutarzan.tarzan.domain.map.entity.clinic.Pharmacy;
import com.mjutarzan.tarzan.domain.map.entity.security.Cctv;
import com.mjutarzan.tarzan.domain.map.entity.security.Police;
import com.mjutarzan.tarzan.domain.map.entity.shopping.ConvenienceStore;
import com.mjutarzan.tarzan.domain.map.entity.shopping.Mart;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Bicycle;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Bus;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Subway;
import com.mjutarzan.tarzan.global.common.service.DataService;
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
    private final DataService csvService;
    private final ApiHouseService apiHouseService;

    @GetMapping("/api/data/test")
    @ResponseBody
    public String testApi(){
        return "ok";
    }

    @GetMapping("/api/data/house")
    @ResponseBody
    public String storeHouseList(){
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
    public String storeCivicCenterList(){
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

    @GetMapping("/api/data/gym")
    @ResponseBody
    public String storeGymList(){
        String filePath = "static/data/gym.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Gym> gymList = csvService.GetEntityListFromStringList(list, Gym.class, 4, 5);

        for (int i=0;i<4;i++){
            log.info("{}", gymList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", gymList.size());

        return "ok";
    }

    @GetMapping("/api/data/park")
    @ResponseBody
    public String storeParkList(){
        String filePath = "static/data/park.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Park> parkList = csvService.GetEntityListFromStringList(list, Park.class, 4, 5);

        for (int i=0;i<4;i++){
            log.info("{}", parkList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", parkList.size());

        return "ok";
    }

    @GetMapping("/api/data/hospital")
    @ResponseBody
    public String storeHospitalList(){
        String filePath = "static/data/hospital.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Hospital> hospitalList = csvService.GetEntityListFromStringList(list, Hospital.class, 3, 4);

        for (int i=0;i<4;i++){
            log.info("{}", hospitalList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", hospitalList.size());

        return "ok";
    }

    @GetMapping("/api/data/medical-clinic")
    @ResponseBody
    public String storeMedicalClinicList(){
        String filePath = "static/data/medical_clinic.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<MedicalClinic> medicalClinicList = csvService.GetEntityListFromStringList(list, MedicalClinic.class, 3, 4);

        for (int i=0;i<4;i++){
            log.info("{}", medicalClinicList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", medicalClinicList.size());

        return "ok";
    }

    @GetMapping("/api/data/pharmacy")
    @ResponseBody
    public String storePharmacyList(){
        String filePath = "static/data/pharmacy.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Pharmacy> pharmacyList = csvService.GetEntityListFromStringList(list, Pharmacy.class, 3, 4);

        for (int i=0;i<4;i++){
            log.info("{}", pharmacyList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", pharmacyList.size());

        return "ok";
    }

    @GetMapping("/api/data/cctv")
    @ResponseBody
    public String storeCctvList(){
        String filePath = "static/data/cctv.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Cctv> cctvList = csvService.GetEntityListFromStringList(list, Cctv.class, 2, 3);

        for (int i=0;i<4;i++){
            log.info("{}", cctvList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", cctvList.size());

        return "ok";
    }

    @GetMapping("/api/data/police")
    @ResponseBody
    public String storePoliceList(){
        String filePath = "static/data/police.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Police> policeList = csvService.GetEntityListFromStringList(list, Police.class, 6, 7);

        for (int i=0;i<4;i++){
            log.info("{}", policeList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", policeList.size());

        return "ok";
    }

    @GetMapping("/api/data/convenience-store")
    @ResponseBody
    public String storeConvenienceStoreList(){
        String filePath = "static/data/convenience_store.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<ConvenienceStore> convenienceStoreList = csvService.GetEntityListFromStringList(list, ConvenienceStore.class, 13, 14);

        for (int i=0;i<4;i++){
            log.info("{}", convenienceStoreList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", convenienceStoreList.size());

        return "ok";
    }
    @GetMapping("/api/data/mart")
    @ResponseBody
    public String storeMartList(){
        String filePath = "static/data/mart.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Mart> martList = csvService.GetEntityListFromStringList(list, Mart.class, 8, 9);

        for (int i=0;i<4;i++){
            log.info("{}", martList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", martList.size());

        return "ok";
    }
    @GetMapping("/api/data/subway")
    @ResponseBody
    public String storeSubwayList(){
        String filePath = "static/data/subway.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Subway> subwayList = csvService.GetEntityListFromStringList(list, Subway.class, 5, 6);

        for (int i=0;i<4;i++){
            log.info("{}", subwayList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", subwayList.size());

        return "ok";
    }

    @GetMapping("/api/data/bus")
    @ResponseBody
    public String storeBusList(){
        String filePath = "static/data/bus_stop.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Bus> busList = csvService.GetEntityListFromStringList(list, Bus.class, 2, 3);

        for (int i=0;i<4;i++){
            log.info("{}", busList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", busList.size());

        return "ok";
    }
//수정 필
    @GetMapping("/api/data/bicycle")
    @ResponseBody
    public String storeBicycleList(){
        String filePath = "static/data/bicycle.csv";
        List<String[]> list = csvService.readCsvFile(filePath);

        List<Bicycle> bicycleList = csvService.GetEntityListFromStringList(list, Bicycle.class, 4, 5);

        for (int i=0;i<4;i++){
            log.info("{}", bicycleList.get(i));
        }

        log.info("바꾸기전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", bicycleList.size());

        return "ok";
    }
}