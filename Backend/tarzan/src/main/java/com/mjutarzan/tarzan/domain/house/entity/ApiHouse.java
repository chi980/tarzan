package com.mjutarzan.tarzan.domain.house.entity;

import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue(value = "api")
@NoArgsConstructor
public class ApiHouse extends House implements DataInstance {

    @Builder
    public ApiHouse(String name, String address, Point location, String category){
        super(name, address, location, category);
    }

    @Override
    public ApiHouse getInstance(String[] info, Point location){
//        대지위치,건물명,동_세부용도,연면적,호수,위도_user,경도_user
        super.setAddress(info[0]);
        super.setName(info[1]);
        super.setLocation(location);
        super.setCategory(info[2]);

        return this;
    }

}
