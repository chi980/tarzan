package com.mjutarzan.tarzan.domain.map.service;

import com.mjutarzan.tarzan.domain.map.api.request.BuildingRequestDto;
import com.mjutarzan.tarzan.domain.map.api.response.BuildingListItemResponseDto;
import com.mjutarzan.tarzan.domain.map.entity.Building;
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
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import com.mjutarzan.tarzan.domain.map.repository.BuildingRepository;
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
import com.mjutarzan.tarzan.global.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BuildingServiceImpl implements BuildingService{

    private final LocationService locationService;
    private final BuildingRepository buildingRepository;
    private final CivicCenterRepository civicCenterRepository;
    private final GymRepository gymRepository;
    private final ParkRepository parkRepository;
    private final HospitalRepository hospitalRepository;
    private final PharmacyRepository pharmacyRepository;
    private final MedicalClinicRepository medicalClinicRepository;
    private final CctvRepository cctvRepository;
    private final PoliceRepository policeRepository;
    private final ConvenienceStoreRepository convenienceStoreRepository;
    private final MartRepository martRepository;
    private final SubwayRepository subwayRepository;
    private final BusRepository busRepository;
    private final BicycleRepository bicycleRepository;
    @Override
    public List<BuildingListItemResponseDto> getBuildings(BuildingRequestDto buildingListRequestDto) {
        Point location = locationService.createPoint(buildingListRequestDto.getLatitude(), buildingListRequestDto.getLongitude());
        Double latitude = buildingListRequestDto.getLatitude();
        Double longitude = buildingListRequestDto.getLongitude();
        double radius = buildingListRequestDto.getRadius();
        List<BuildingListItemResponseDto> list = null;

        switch (buildingListRequestDto.getType()) {
            case ALL -> {
                list = buildingRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(building -> BuildingListItemResponseDto.builder()
                                .name(building.getName())
                                .category(building.getCategory())
                                .address(building.getAddress())
                                .latitude(building.getLocation().getX())
                                .longitude(building.getLocation().getY())
                                .type(getBuildingType(building))
                                .build())
                        .collect(Collectors.toList());
            }
            case CIVIC_CENTER -> {
                list = civicCenterRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(civicCenter -> BuildingListItemResponseDto.builder()
                                .name(civicCenter.getName())
                                .category(civicCenter.getCategory())
                                .address(civicCenter.getAddress())
                                .latitude(civicCenter.getLocation().getX())
                                .longitude(civicCenter.getLocation().getY())
                                .type(BuildingType.CIVIC_CENTER)
                                .build())
                        .collect(Collectors.toList());
            }
            case GYM -> {
                list = gymRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(gym -> BuildingListItemResponseDto.builder()
                                .name(gym.getName())
                                .category(gym.getCategory())
                                .address(gym.getAddress())
                                .latitude(gym.getLocation().getX())
                                .longitude(gym.getLocation().getY())
                                .type(BuildingType.GYM)
                                .build())
                        .collect(Collectors.toList());
            }
            case PARK -> {
                list = parkRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(park -> BuildingListItemResponseDto.builder()
                                .name(park.getName())
                                .category(park.getCategory())
                                .address(park.getAddress())
                                .latitude(park.getLocation().getX())
                                .longitude(park.getLocation().getY())
                                .type(BuildingType.PARK)
                                .build())
                        .collect(Collectors.toList());
            }
            case HOSPITAL -> {
                list = hospitalRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(hospital -> BuildingListItemResponseDto.builder()
                                .name(hospital.getName())
                                .category(hospital.getCategory())
                                .address(hospital.getAddress())
                                .latitude(hospital.getLocation().getX())
                                .longitude(hospital.getLocation().getY())
                                .type(BuildingType.HOSPITAL)
                                .build())
                        .collect(Collectors.toList());
            }
            case PHARMACY -> {
                list = pharmacyRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(pharmacy -> BuildingListItemResponseDto.builder()
                                .name(pharmacy.getName())
                                .category(pharmacy.getCategory())
                                .address(pharmacy.getAddress())
                                .latitude(pharmacy.getLocation().getX())
                                .longitude(pharmacy.getLocation().getY())
                                .type(BuildingType.PHARMACY)
                                .build())
                        .collect(Collectors.toList());
            }
            case MEDICAL_CLINIC -> {
                list = medicalClinicRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(clinic -> BuildingListItemResponseDto.builder()
                                .name(clinic.getName())
                                .category(clinic.getCategory())
                                .address(clinic.getAddress())
                                .latitude(clinic.getLocation().getX())
                                .longitude(clinic.getLocation().getY())
                                .type(BuildingType.MEDICAL_CLINIC)
                                .build())
                        .collect(Collectors.toList());
            }
            case CCTV -> {
                list = cctvRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(cctv -> BuildingListItemResponseDto.builder()
                                .name(cctv.getName())
                                .category(cctv.getCategory())
                                .address(cctv.getAddress())
                                .latitude(cctv.getLocation().getX())
                                .longitude(cctv.getLocation().getY())
                                .type(BuildingType.CCTV)
                                .build())
                        .collect(Collectors.toList());
            }
            case POLICE -> {
                list = policeRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(police -> BuildingListItemResponseDto.builder()
                                .name(police.getName())
                                .category(police.getCategory())
                                .address(police.getAddress())
                                .latitude(police.getLocation().getX())
                                .longitude(police.getLocation().getY())
                                .type(BuildingType.POLICE)
                                .build())
                        .collect(Collectors.toList());
            }
            case CONVENIENCE_STORE -> {
                list = convenienceStoreRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(store -> BuildingListItemResponseDto.builder()
                                .name(store.getName())
                                .category(store.getCategory())
                                .address(store.getAddress())
                                .latitude(store.getLocation().getX())
                                .longitude(store.getLocation().getY())
                                .type(BuildingType.CONVENIENCE_STORE)
                                .build())
                        .collect(Collectors.toList());
            }
            case MART -> {
                list = martRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(mart -> BuildingListItemResponseDto.builder()
                                .name(mart.getName())
                                .category(mart.getCategory())
                                .address(mart.getAddress())
                                .latitude(mart.getLocation().getX())
                                .longitude(mart.getLocation().getY())
                                .type(BuildingType.MART)
                                .build())
                        .collect(Collectors.toList());
            }
            case SUBWAY -> {
                list = subwayRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(subway -> BuildingListItemResponseDto.builder()
                                .name(subway.getName())
                                .category(subway.getCategory())
                                .address(subway.getAddress())
                                .latitude(subway.getLocation().getX())
                                .longitude(subway.getLocation().getY())
                                .type(BuildingType.SUBWAY)
                                .build())
                        .collect(Collectors.toList());
            }
            case BUS -> {
                list = busRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(bus -> BuildingListItemResponseDto.builder()
                                .name(bus.getName())
                                .category(bus.getCategory())
                                .address(bus.getAddress())
                                .latitude(bus.getLocation().getX())
                                .longitude(bus.getLocation().getY())
                                .type(BuildingType.BUS)
                                .build())
                        .collect(Collectors.toList());
            }
            case BICYCLE -> {
                list = bicycleRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(bicycle -> BuildingListItemResponseDto.builder()
                                .name(bicycle.getName())
                                .category(bicycle.getCategory())
                                .address(bicycle.getAddress())
                                .latitude(bicycle.getLocation().getX())
                                .longitude(bicycle.getLocation().getY())
                                .type(BuildingType.BICYCLE)
                                .build())
                        .collect(Collectors.toList());
            }
        }
        return list;
    }

    private BuildingType getBuildingType(Building building) {
        if (building instanceof CivicCenter) return BuildingType.CIVIC_CENTER;
        else if (building instanceof Gym) return BuildingType.GYM;
        else if (building instanceof Park) return BuildingType.PARK;
        else if (building instanceof Hospital) return BuildingType.HOSPITAL;
        else if (building instanceof Pharmacy) return BuildingType.PHARMACY;
        else if (building instanceof MedicalClinic) return BuildingType.MEDICAL_CLINIC;
        else if (building instanceof Cctv) return BuildingType.CCTV;
        else if (building instanceof Police) return BuildingType.POLICE;
        else if (building instanceof ConvenienceStore) return BuildingType.CONVENIENCE_STORE;
        else if (building instanceof Mart) return BuildingType.MART;
        else if (building instanceof Subway) return BuildingType.SUBWAY;
        else if (building instanceof Bus) return BuildingType.BUS;
        else if (building instanceof Bicycle) return BuildingType.BICYCLE;
        return BuildingType.ALL;
    }
}
