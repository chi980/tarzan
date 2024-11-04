package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbLnOpendataSaleVResponseWrapper {
    @JsonProperty("tbLnOpendataRtmsV")
    private TbLnOpendataSaleVResponse tbLnOpendataSaleV;
}
