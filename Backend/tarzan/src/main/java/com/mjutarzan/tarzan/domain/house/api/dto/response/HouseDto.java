package com.mjutarzan.tarzan.domain.house.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HouseDto {
    private Long houseId;
    private String houseName;
}
