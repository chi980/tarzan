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
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

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

    private static final Map<Class<? extends Building>, BuildingType> buildingTypeMap = Map.ofEntries(
            entry(CivicCenter.class, BuildingType.CIVIC_CENTER),
            entry(Gym.class, BuildingType.GYM),
            entry(Park.class, BuildingType.PARK),
            entry(Hospital.class, BuildingType.HOSPITAL),
            entry(Pharmacy.class, BuildingType.PHARMACY),
            entry(MedicalClinic.class, BuildingType.MEDICAL_CLINIC),
            entry(Cctv.class, BuildingType.CCTV),
            entry(Police.class, BuildingType.POLICE),
            entry(ConvenienceStore.class, BuildingType.CONVENIENCE_STORE),
            entry(Mart.class, BuildingType.MART),
            entry(Subway.class, BuildingType.SUBWAY),
            entry(Bus.class, BuildingType.BUS),
            entry(Bicycle.class, BuildingType.BICYCLE)
    );

    @Override
    public List<BuildingListItemResponseDto> getBuildings(BuildingRequestDto buildingListRequestDto) {
        Point location = locationService.createPoint(buildingListRequestDto.getLatitude(), buildingListRequestDto.getLongitude());
        Double latitude = buildingListRequestDto.getLatitude();
        Double longitude = buildingListRequestDto.getLongitude();
        double radius = buildingListRequestDto.getRadius();
        List<BuildingListItemResponseDto> list;

        switch (buildingListRequestDto.getType()) {
            case ALL -> {
                list = buildingRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(building -> mapToDto(building, getBuildingType(building)))
                        .collect(Collectors.toList());
            }
            case CIVIC_CENTER -> {
                list = civicCenterRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(civicCenter -> mapToDto(civicCenter, BuildingType.CIVIC_CENTER))
                        .collect(Collectors.toList());
            }
            case GYM -> {
                list = gymRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(gym -> mapToDto(gym, BuildingType.GYM))
                        .collect(Collectors.toList());
            }
            case PARK -> {
                list = parkRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(park -> mapToDto(park, BuildingType.PARK))
                        .collect(Collectors.toList());
            }
            case HOSPITAL -> {
                list = hospitalRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(hospital -> mapToDto(hospital, BuildingType.HOSPITAL))
                        .collect(Collectors.toList());
            }
            case PHARMACY -> {
                list = pharmacyRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(pharmacy -> mapToDto(pharmacy, BuildingType.PHARMACY))
                        .collect(Collectors.toList());
            }
            case MEDICAL_CLINIC -> {
                list = medicalClinicRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(clinic -> mapToDto(clinic, BuildingType.MEDICAL_CLINIC))
                        .collect(Collectors.toList());
            }
            case CCTV -> {
                list = cctvRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(cctv -> mapToDto(cctv, BuildingType.CCTV))
                        .collect(Collectors.toList());
            }
            case POLICE -> {
                list = policeRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(police -> mapToDto(police, BuildingType.POLICE))
                        .collect(Collectors.toList());
            }
            case CONVENIENCE_STORE -> {
                list = convenienceStoreRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(store -> mapToDto(store, BuildingType.CONVENIENCE_STORE))
                        .collect(Collectors.toList());
            }
            case MART -> {
                list = martRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(mart -> mapToDto(mart, BuildingType.MART))
                        .collect(Collectors.toList());
            }
            case SUBWAY -> {
                list = subwayRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(subway -> mapToDto(subway, BuildingType.SUBWAY))
                        .collect(Collectors.toList());
            }
            case BUS -> {
                list = busRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(bus -> mapToDto(bus, BuildingType.BUS))
                        .collect(Collectors.toList());
            }
            case BICYCLE -> {
                list = bicycleRepository.findAllWithinRadius(longitude, latitude, radius).stream()
                        .map(bicycle -> mapToDto(bicycle, BuildingType.BICYCLE))
                        .collect(Collectors.toList());
            }
            default -> throw new IllegalArgumentException("Invalid building type: " + buildingListRequestDto.getType());
        }

        return list;
    }

    private BuildingListItemResponseDto mapToDto(Building building, BuildingType type) {
        return BuildingListItemResponseDto.builder()
                .name(building.getName())
                .category(building.getCategory())
                .address(building.getAddress())
                .latitude(building.getLocation().getX())
                .longitude(building.getLocation().getY())
                .type(type)
                .build();
    }

    private BuildingType getBuildingType(Building building) {
        return buildingTypeMap.getOrDefault(building.getClass(), BuildingType.ALL);
    }
}
