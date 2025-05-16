package com.mjutarzan.tarzan.domain.report.api;


import com.mjutarzan.tarzan.domain.report.api.request.ReportRequestDto;
import com.mjutarzan.tarzan.domain.report.service.ReportService;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportApi {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> report(
            @RequestBody @Valid ReportRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDto) {

        try {
            reportService.report(requestDto, userDto);
            return ResponseEntity.ok().body(
                    BaseResponseDto.builder()
                            .success(true)
                            .message("완료되었습니다.")
                            .build()
            );

        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body(
                    BaseResponseDto.builder()
                            .success(false)
                            .message("메일 전송에 실패했습니다.")
                            .build()
            );
        }
    }
}
