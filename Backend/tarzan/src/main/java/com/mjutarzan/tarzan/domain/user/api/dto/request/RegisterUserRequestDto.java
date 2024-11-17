package com.mjutarzan.tarzan.domain.user.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequestDto {

    @JsonProperty("user_image_url")
    private String imageUrl;

    @JsonProperty("user_nickname")
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @JsonProperty("user_gu")
    @NotNull(message = "시군구는 필수입니다.")  // NotBlank 대신 NotNull 사용
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
