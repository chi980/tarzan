package com.mjutarzan.tarzan.global.oauth2.handler;

import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.jwt.service.JwtService;
import com.mjutarzan.tarzan.global.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler  implements AuthenticationSuccessHandler {

    @Value("${front.base-url}")
    private String frontBaseUrl;

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            String accessToken = jwtService.generateAccessToken(oAuth2User.getEmail());
            String refreshToken = jwtService.generateRefreshToken();
            jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
            String redirectUrl = frontBaseUrl + "/login-processing";

            log.info("onAuthenticationSuccess: 로그인 성공");
            if(oAuth2User.getRole() == Role.GUEST) {
                redirectUrl += ("?access_token=" + accessToken + "&refresh_token="+refreshToken+"&role="+oAuth2User.getRole());

            } else {
                redirectUrl += ("?access_token=" + accessToken + "&refresh_token=" + refreshToken+"&gu="+oAuth2User.getGu()+"&nickname="+oAuth2User.getNickname()+"&role="+oAuth2User.getRole()); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            log.error("onAuthenticationSuccess: 로그인 실패!");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw e;
        }

    }
}
