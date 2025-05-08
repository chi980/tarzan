package com.mjutarzan.tarzan.domain.map.service;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingCategory;
import com.mjutarzan.tarzan.domain.map.repository.amenity.AmenityRepository;
import com.mjutarzan.tarzan.domain.map.repository.clinic.ClinicRepository;
import com.mjutarzan.tarzan.domain.map.repository.security.SecurityRepository;
import com.mjutarzan.tarzan.domain.map.repository.shopping.ShoppingRepository;
import com.mjutarzan.tarzan.domain.map.repository.transportation.TransportationRepository;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class IndexService {
    private final AmenityRepository amenityRepository;
    private final ClinicRepository clinicRepository;
    private final SecurityRepository securityRepository;
    private final ShoppingRepository shoppingRepository;
    private final TransportationRepository transportationRepository;

    public Map<BuildingCategory, Long> getIndex(Double longitude, Double latitude, Map<BuildingCategory, Double> radiuses) {
        Map<BuildingCategory, Long> indexMap = new HashMap<>();

        for (BuildingCategory category : BuildingCategory.values()) {
            Double radius = radiuses.get(category);
            if (radius != null) {
                Long count = getBuildingCountByCategory(category, longitude, latitude, radius);
                indexMap.put(category, count);
            }
        }

        return indexMap;
    }

    public Map<BuildingCategory, Double> getRadius(){

        Map<BuildingCategory, Double> radiuses = new HashMap<>();
        radiuses.put(BuildingCategory.SHOPPING, 1000.0); // 1km 반경 (쇼핑)
        radiuses.put(BuildingCategory.CLINIC, 1000.0);  // 1km 반경 (병원/클리닉)
        radiuses.put(BuildingCategory.SECURITY, 1000.0); // 1km 반경 (보안시설)
        radiuses.put(BuildingCategory.TRANSPORTATION, 1000.0); // 1km 반경 (교통시설)
        radiuses.put(BuildingCategory.AMENITY, 500.0); // 500m 반경 (기타 편의시설)

        return radiuses;
    }

    private Long getBuildingCountByCategory(BuildingCategory category, Double longitude, Double latitude, Double radius) {
        switch (category) {
            case AMENITY:
                return amenityRepository.countAllWithinRadius(longitude, latitude, radius);
            case CLINIC:
                return clinicRepository.countAllWithinRadius(longitude, latitude, radius);
            case SECURITY:
                return securityRepository.countAllWithinRadius(longitude, latitude, radius);
            case SHOPPING:
                return shoppingRepository.countAllWithinRadius(longitude, latitude, radius);
            case TRANSPORTATION:
                return transportationRepository.countAllWithinRadius(longitude, latitude, radius);
            default:
                throw new IllegalArgumentException("Unknown building category: " + category);
        }
    }
}


