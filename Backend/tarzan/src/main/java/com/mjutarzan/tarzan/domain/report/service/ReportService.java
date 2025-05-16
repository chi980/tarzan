package com.mjutarzan.tarzan.domain.report.service;

import com.mjutarzan.tarzan.domain.report.api.request.ReportRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

public interface ReportService {
    void report(@Valid ReportRequestDto requestDto, CustomUserDetails userDto) throws MessagingException;
}
