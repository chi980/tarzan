package com.mjutarzan.tarzan.domain.user.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {


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

    @Builder
    public UserResponseDto(String imageUrl, String nickname, SiGunGu gu, Boolean haveAnimal,Boolean haveCar, String jobAddress, Double latitude, Double longitude){
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;
        this.jobAddress = jobAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
