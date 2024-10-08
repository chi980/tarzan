package com.mjutarzan.tarzan.domain.map.repository.shopping;

import com.mjutarzan.tarzan.domain.map.entity.amenity.CivicCenter;
import com.mjutarzan.tarzan.domain.map.entity.shopping.ConvenienceStore;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConvenienceStoreRepository extends JpaRepository<ConvenienceStore, Long> {


    @Query(value = "SELECT * FROM convenience_store cs WHERE ST_DWithin(cs.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) = true", nativeQuery = true)
    List<CivicCenter> findAllWithinRadius(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("radius") double radius);
}
