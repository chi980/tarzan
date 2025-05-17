package com.mjutarzan.tarzan.domain.report.api.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mjutarzan.tarzan.domain.report.model.vo.ReportReasonType;
import com.mjutarzan.tarzan.domain.report.model.vo.ReportTargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ReportRequestDto {

    @NotNull
    private ReportTargetType reportTargetType;

    @NotNull
    private Long reportTargetId;

    @NotNull
    private ReportReasonType reportReasonType;

    @JsonProperty("report-reason-content")
    private String reportReasonContent;

}
