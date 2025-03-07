package com.mjutarzan.tarzan.global.oauth2.handler;

import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.jwt.JwtService;
import com.mjutarzan.tarzan.global.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler  implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            System.out.println(oAuth2User.getRole());
            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//                String refreshToken = jwtService.createRefreshToken();
//                response.addHeader(jwtService.getRefreshHeader(), "Bearer "+refreshToken);
                // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
                log.info("현재 사용자는 GUEST입니당");
                String redirectUrl = "http://localhost:5173/login-processing?access_token=" + accessToken + "&refresh_token=&role="+oAuth2User.getRole();
                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                log.info("redireciont 하자: {}", redirectUrl);
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect(redirectUrl);
            } else {
                log.info("현재 사용자는 USER입니당");

                String redirectUrl = loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
                response.sendRedirect(redirectUrl);
//                response.setStatus(HttpServletResponse.SC_OK);

            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private String loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
//        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        String redirectUrl = "http://localhost:5173/login-processing?access_token=" + accessToken + "&refresh_token=" + refreshToken+"&gu="+oAuth2User.getGu()+"&nickname="+oAuth2User.getNickname()+"&role="+oAuth2User.getRole();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

        return redirectUrl;
    }
}
