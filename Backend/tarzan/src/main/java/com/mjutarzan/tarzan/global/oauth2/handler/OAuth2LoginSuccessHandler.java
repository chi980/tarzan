package com.mjutarzan.tarzan.global.oauth2.handler;

import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.global.jwt.JwtTokenProvider;
import com.mjutarzan.tarzan.global.oauth2.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
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

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            String accessToken = jwtTokenProvider.generateAccessToken(oAuth2User.getEmail());
            String refreshToken = jwtTokenProvider.generateRefreshToken(oAuth2User.getEmail());
            Cookie refreshTokenCookie = jwtTokenProvider.generateRefreshTokenCookie(refreshToken);
            response.addCookie(refreshTokenCookie);

            log.info("onAuthenticationSuccess: 로그인 성공");
            log.info("accessToken: {}", accessToken);
            log.info("accessToken is Exipred at {}", jwtTokenProvider.getExpirationDateFromToken(accessToken, false));
            log.info("refreshToken: {}", refreshToken);
            log.info("accessToken is Exipred at {}", jwtTokenProvider.getExpirationDateFromToken(refreshToken, true));

            // 프론트엔드로 Access Token만 전달 (Refresh Token은 쿠키에 저장되므로 URL에서 제거)
            String redirectUrl = frontBaseUrl + "/login-processing" +
                    "?access_token=" + accessToken +
                    "&email=" + oAuth2User.getEmail() +
                    "&role=" + oAuth2User.getRole();

            // GUEST 여부에 따라 추가 정보 전달
            if (oAuth2User.getRole() != Role.GUEST) {
                redirectUrl += "&gu=" + oAuth2User.getGu() + "&nickname=" + oAuth2User.getNickname();
            }

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            log.error("onAuthenticationSuccess: 로그인 실패!", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
