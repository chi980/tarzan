package com.mjutarzan.tarzan.global.oauth2.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${front.base-url}")
    private String frontBaseUrl;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());

        String redirectUrl = frontBaseUrl + "/login-failure?message=" + URLEncoder.encode("소셜 로그인 실패!", "UTF-8");
        response.sendRedirect(redirectUrl);
    }
}
