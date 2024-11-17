package com.mjutarzan.tarzan.domain.user.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequestDto {

    @JsonProperty("user_image_url")
    private String imageUrl;

    @JsonProperty("user_nickname")
    private String nickname;

    @JsonProperty("user_gu")
    private SiGunGu gu;

    @JsonProperty("user_have_animal")
    private Boolean haveAnimal;

    @JsonProperty("user_have_car")
    private Boolean haveCar;

    @JsonProperty("user_job_address")
    private String jobAddress;

    @JsonProperty("user_latitude")
    private Double latitude;

    @JsonProperty("user_longitude")
    private Double longitude;
}
