package com.mjutarzan.tarzan.domain.map.service;

import com.mjutarzan.tarzan.domain.map.entity.Building;
import com.mjutarzan.tarzan.domain.map.model.dto.NormalizationParam;
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingCategory;
import com.mjutarzan.tarzan.domain.map.repository.amenity.AmenityRepository;
import com.mjutarzan.tarzan.domain.map.repository.clinic.ClinicRepository;
import com.mjutarzan.tarzan.domain.map.repository.security.SecurityRepository;
import com.mjutarzan.tarzan.domain.map.repository.shopping.ShoppingRepository;
import com.mjutarzan.tarzan.domain.map.repository.transportation.TransportationRepository;
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
    private static final Map<BuildingCategory, Double> RADIUS_MAP = Map.of(
            BuildingCategory.SHOPPING, 1000.0,         // 1.0 km
            BuildingCategory.CLINIC, 500.0,            // 0.5 km
            BuildingCategory.SECURITY, 100.0,          // 0.1 km
            BuildingCategory.TRANSPORTATION, 1000.0,   // 1.0 km
            BuildingCategory.AMENITY, 1000.0           // 1.0 km
    );

    private static final Map<BuildingCategory, NormalizationParam> normalizationParams = Map.of(
            BuildingCategory.SHOPPING, new NormalizationParam(0, 179, 119),
            BuildingCategory.CLINIC, new NormalizationParam(0, 506, 146),
            BuildingCategory.SECURITY, new NormalizationParam(Math.log1p(0), Math.log1p(103), Math.log1p(19)),
            BuildingCategory.TRANSPORTATION, new NormalizationParam(0, 132, 89),
            BuildingCategory.AMENITY, new NormalizationParam(0, 56, 41)
    );



    public Map<BuildingCategory, Double> getIndex(Double longitude, Double latitude, Map<BuildingCategory, Double> radiuses) {
        Map<BuildingCategory, Double> indexMap = new HashMap<>();

        for (BuildingCategory category : BuildingCategory.values()) {
            Double radius = radiuses.get(category);
            if (radius != null) {
                Double score = getBuildingScoreByCategory(category, longitude, latitude, radius);
                indexMap.put(category, score);
            }
        }

        return indexMap;
    }

    public Map<BuildingCategory, Double> getRadius(){
        return RADIUS_MAP;
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
    private Double getBuildingScoreByCategory(BuildingCategory category, Double longitude, Double latitude, Double radius) {
        Long rawCount = getBuildingCountByCategory(category, longitude, latitude, radius);
        NormalizationParam param = normalizationParams.get(category);

        if (param == null) {
            throw new IllegalArgumentException("No normalization parameter defined for " + category);
        }

        switch (category) {
            case SECURITY:
                double logVal = Math.log1p(rawCount);
                double clippedLog = Math.min(logVal, param.getQ95());
                return (clippedLog - param.getMin()) / (param.getMax() - param.getMin()) * 100.0;

            case CLINIC:
                long clippedClinic = Math.min(rawCount, (long) param.getQ95());
                return (clippedClinic - param.getMin()) / (param.getMax() - param.getMin()) * 100.0;

            case SHOPPING:
            case TRANSPORTATION:
            case AMENITY:
                return (rawCount - param.getMin()) / (param.getMax() - param.getMin()) * 100.0;

            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
    public double getTotalScore(Map<BuildingCategory, Double> scoreMap) {
        double total = 0.0;

        for (BuildingCategory category : BuildingCategory.values()) {
            double weight = category == BuildingCategory.AMENITY ? 0.5 : 1.0;
            double score = scoreMap.getOrDefault(category, 0.0);
            total += score * weight;
        }

        return total / 4.5;
    }
    public double getTotalScore(Double longitude, Double latitude) {
        double total = 0.0;

        for (BuildingCategory category : BuildingCategory.values()) {
            double weight = category == BuildingCategory.AMENITY ? 0.5 : 1.0;
            Double radius = RADIUS_MAP.get(category);

            if (radius == null) {
                throw new IllegalArgumentException("No radius defined for category: " + category);
            }

            double score = getBuildingScoreByCategory(category, longitude, latitude, radius);
            total += score * weight;
        }

        return total / 4.5; // 가중치 합계 (1 + 1 + 1 + 1 + 0.5)
    }




}


