package com.mjutarzan.tarzan.global.common.service;

import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
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
import com.mjutarzan.tarzan.domain.map.repository.amenity.CivicCenterRepository;
import com.mjutarzan.tarzan.domain.map.repository.amenity.GymRepository;
import com.mjutarzan.tarzan.domain.map.repository.amenity.ParkRepository;
import com.mjutarzan.tarzan.domain.map.repository.clinic.HospitalRepository;
import com.mjutarzan.tarzan.domain.map.repository.clinic.MedicalClinicRepository;
import com.mjutarzan.tarzan.domain.map.repository.clinic.PharmacyRepository;
import com.mjutarzan.tarzan.domain.map.repository.security.CctvRepository;
import com.mjutarzan.tarzan.domain.map.repository.security.PoliceRepository;
import com.mjutarzan.tarzan.domain.map.repository.shopping.ConvenienceStoreRepository;
import com.mjutarzan.tarzan.domain.map.repository.shopping.MartRepository;
import com.mjutarzan.tarzan.domain.map.repository.transportation.BicycleRepository;
import com.mjutarzan.tarzan.domain.map.repository.transportation.BusRepository;
import com.mjutarzan.tarzan.domain.map.repository.transportation.SubwayRepository;
import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DataService {

    private final LocationService locationService;
    private final ApiHouseRepository apiHouseRepository;
    private final CivicCenterRepository civicCenterRepository;
    private final GymRepository gymRepository;
    private final ParkRepository parkRepository;
    private final HospitalRepository hospitalRepository;
    private final MedicalClinicRepository medicalClinicRepository;
    private final PharmacyRepository pharmacyRepository;
    private final CctvRepository cctvRepository;
    private final PoliceRepository policeRepository;
    private final ConvenienceStoreRepository convenienceStoreRepository;
    private final MartRepository martRepository;
    private final BicycleRepository bicycleRepository;
    private final BusRepository busRepository;
    private final SubwayRepository subwayRepository;


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

//    public <S extends DataInstance> List<S> GetEntityListFromStringList(List<String[]> list, Class<S> clazz, int latitudeIdx, int longitudeIdx) {
//        return (List<S>) list.stream()
//                .skip(1) // 첫 번째 항목 건너뛰기
//                .map(info -> {
//                    try {
//                        // 여기서 클래스의 getInstance 메서드를 호출해 S 타입 객체를 생성
//                        Double latitude = Double.parseDouble(info[latitudeIdx]);
//                        Double longitude = Double.parseDouble(info[longitudeIdx]);
//                        Point location = locationService.createPoint(latitude, longitude);
//
//                        // Reflection을 통해 getInstance 호출
//                        return clazz.getDeclaredConstructor().newInstance().getInstance(info, location);
//                    } catch (Exception e) {
//                        log.error("{}", e.getMessage());
//                        log.error("오류: {}", Arrays.toString(info));
//                        // 예외가 발생하면 null 반환 (처리 방식에 따라 다르게 처리 가능)
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull) // null이 아닌 항목만 필터링
//                .collect(Collectors.toList());
//    }

    public <S extends DataInstance> List<S> GetEntityListFromStringList(List<String[]> list, Class<S> clazz, int latitudeIdx, int longitudeIdx) {
        List<S> resultList = new ArrayList<>();
        for (String[] info : list.stream().skip(1).collect(Collectors.toList())) {
            try {
                Double latitude = Double.parseDouble(info[latitudeIdx]);
                Double longitude = Double.parseDouble(info[longitudeIdx]);
                Point location = locationService.createPoint(latitude, longitude);

                // Reflection을 통해 getInstance 호출
                S instance = (S) clazz.getDeclaredConstructor().newInstance().getInstance(info, location);
                resultList.add(instance);
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                log.error("오류: {}", Arrays.toString(info));
            }
        }
        return resultList;
    }


    public <S extends DataInstance, R extends JpaRepository<S, Long>> void storeEntityList(
            String filePath, int latitudeIdx, int longitudeIdx, Class<S> clazz, R repository) {

        List<String[]> list = readCsvFile(filePath);
        List<S> entityList = GetEntityListFromStringList(list, clazz, latitudeIdx, longitudeIdx);

        log.info("바꾸기 전 길이: {}", list.size());
        log.info("바꾼 후 길이: {}", entityList.size());

        repository.saveAll(entityList);
    }

    // 아래는 모든 메서드를 storeEntityList를 이용하여 간결화한 예시입니다.
    public void storeHouseList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, ApiHouse.class, apiHouseRepository);
    }

    public void storeCivicCenterList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, CivicCenter.class, civicCenterRepository);
    }

    public void storeGymList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Gym.class, gymRepository);
    }

    public void storeParkList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Park.class, parkRepository);
    }

    public void storeHospitalList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Hospital.class, hospitalRepository);
    }

    public void storeMedicalClinicList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, MedicalClinic.class, medicalClinicRepository);
    }

    public void storePharmacyList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Pharmacy.class, pharmacyRepository);
    }

    public void storeCctvList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Cctv.class, cctvRepository);
    }

    public void storePoliceList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Police.class, policeRepository);
    }

    public void storeConvenienceStoreList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, ConvenienceStore.class, convenienceStoreRepository);
    }

    public void storeMartList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Mart.class, martRepository);
    }

    public void storeBicycleList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Bicycle.class, bicycleRepository);
    }

    public void storeBusList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Bus.class, busRepository);
    }

    public void storeSubwayList(String filePath, int latitudeIdx, int longitudeIdx) {
        storeEntityList(filePath, latitudeIdx, longitudeIdx, Subway.class, subwayRepository);
    }
}
