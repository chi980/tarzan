package com.mjutarzan.tarzan.domain.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseCsvDto {
    private String address;
    private String name;
    private String category;
    private Double latitude;
    private Double longitude;
}
