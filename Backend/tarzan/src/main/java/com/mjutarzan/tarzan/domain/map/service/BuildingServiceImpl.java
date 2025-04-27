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
    public List<BuildingListItemResponseDto> getBuildings(BuildingRequestDto requestDto) {
        double latitude = requestDto.getLatitude();
        double longitude = requestDto.getLongitude();
        double radius = requestDto.getRadius();

        log.info("latitude: {}", latitude);
        log.info("longitude: {}", longitude);

        List<? extends Building> buildings;


        switch (requestDto.getType()) {
            case ALL -> buildings = buildingRepository.findAllWithinRadius(longitude, latitude, radius);
            case CIVIC_CENTER -> buildings = civicCenterRepository.findAllWithinRadius(longitude, latitude, radius);
            case GYM -> buildings = gymRepository.findAllWithinRadius(longitude, latitude, radius);
            case PARK -> buildings = parkRepository.findAllWithinRadius(longitude, latitude, radius);
            case HOSPITAL -> buildings = hospitalRepository.findAllWithinRadius(longitude, latitude, radius);
            case PHARMACY -> buildings = pharmacyRepository.findAllWithinRadius(longitude, latitude, radius);
            case MEDICAL_CLINIC -> buildings = medicalClinicRepository.findAllWithinRadius(longitude, latitude, radius);
            case CCTV -> buildings = cctvRepository.findAllWithinRadius(longitude, latitude, radius);
            case POLICE -> buildings = policeRepository.findAllWithinRadius(longitude, latitude, radius);
            case CONVENIENCE_STORE -> buildings = convenienceStoreRepository.findAllWithinRadius(longitude, latitude, radius);
            case MART -> buildings = martRepository.findAllWithinRadius(longitude, latitude, radius);
            case SUBWAY -> buildings = subwayRepository.findAllWithinRadius(longitude, latitude, radius);
            case BUS -> buildings = busRepository.findAllWithinRadius(longitude, latitude, radius);
            case BICYCLE -> buildings = bicycleRepository.findAllWithinRadius(longitude, latitude, radius);
            default -> throw new IllegalArgumentException("Invalid building type: " + requestDto.getType());
        }

        return buildings.stream()
                .map(building -> mapToDto(building, getBuildingType(building)))
                .collect(Collectors.toList());
    }

    private BuildingListItemResponseDto mapToDto(Building building, BuildingType type) {
        return BuildingListItemResponseDto.builder()
                .name(building.getName())
                .category(building.getCategory())
                .address(building.getAddress())
                .latitude(building.getLocation().getY())  // 위도 (Latitude)
                .longitude(building.getLocation().getX()) // 경도 (Longitude)
                .type(type)
                .build();
    }

    private BuildingType getBuildingType(Building building) {
        return buildingTypeMap.getOrDefault(building.getClass(), BuildingType.ALL);
    }
}
