package com.mjutarzan.tarzan.domain.report.api.request;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ReportRequestDto {

    @JsonProperty("report-target-type")
    @NotNull
    private ReportTargetType reportTargetType;

    @JsonProperty("report-target-id")
    @NotNull
    private Long reportTargetId;

    @JsonProperty("report-reason-type")
    @NotNull
    private ReportReasonType reportReasonType;

    @JsonProperty("report-reason-content")
    private String reportReasonContent;

    @JsonProperty("reported-at")
    private LocalDateTime reportedAt;
}
