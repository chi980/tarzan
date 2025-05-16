package com.mjutarzan.tarzan.domain.report.service;


import com.mjutarzan.tarzan.domain.report.api.request.ReportRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService{

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${app.admin.email}")
    private String adminEmail;


    @Override
    public void report(ReportRequestDto requestDto, CustomUserDetails userDto) throws MessagingException {

        // 1) Thymeleaf 컨텍스트에 변수 바인딩
        Context ctx = new Context();
        ctx.setVariable("targetType", requestDto.getReportTargetType());
        ctx.setVariable("targetId", requestDto.getReportTargetId());
        ctx.setVariable("reasonType", requestDto.getReportReasonType());
        ctx.setVariable("reasonContent", requestDto.getReportReasonContent());

        ctx.setVariable("reporterId", userDto.getId());
        ctx.setVariable("reporterEmail",userDto.getEmail());
        ctx.setVariable("reportedAt",
                requestDto.getReportedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        // 2) 템플릿 프로세스 → HTML 문자열 생성
        String html = templateEngine.process("report", ctx);

        // 3) MimeMessageHelper 로 HTML 메일 설정
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8"
        );
        helper.setTo(adminEmail);
        helper.setSubject("[신고 알림] " + requestDto.getReportTargetType() + " #" + requestDto.getReportTargetId());
        helper.setText(html, true);  // true = HTML 모드

        // 4) 전송
        mailSender.send(msg);
    }
}
