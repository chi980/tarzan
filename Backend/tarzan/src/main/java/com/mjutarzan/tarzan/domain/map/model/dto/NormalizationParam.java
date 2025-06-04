package com.mjutarzan.tarzan.domain.map.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NormalizationParam {
    private final double min;
    private final double max;
    private final double q95;
}
