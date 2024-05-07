package com.mjutarzan.tarzan.global.security.handler;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import com.mjutarzan.tarzan.global.security.token.GeneratedToken;
import com.mjutarzan.tarzan.global.security.token.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final JwtTokenUtil jwtUtil;
    private final RefreshTokenRepository tokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = oAuth2User.getUsername();


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

//        String token = jwtUtil.createJwt(username, role, accessTokenValidTime);

        // 토큰 생성
//        String accessToken =  jwtUtil.createAccessToken(oAuth2User.getUserDTO().getUsername(), oAuth2User.getUserDTO().getRole());
        // 발급시 마다 저장..
//        String refreshToken =  jwtUtil.createRefreshToken(oAuth2User.getUserDTO().getUsername(), oAuth2User.getUserDTO().getRole(), accessToken);
        GeneratedToken token = jwtUtil.createToken(oAuth2User.getUserDTO().getUsername(), oAuth2User.getUserDTO().getRole());
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        log.info("login 성공! token; {}", accessToken);



        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
        response.addCookie(jwtUtil.createCookie("Authorization", accessToken.split(" ")[1]));
        response.addCookie(jwtUtil.createCookie("refresh", refreshToken));
//        Map<String, String> responseMap = new HashMap<>();
//        responseMap.put("AT_HEADER", accessToken);
//        responseMap.put("RT_HEADER", refreshToken);
//        new ObjectMapper().writeValue(response.getWriter(), responseMap);

//        response.sendRedirect("http://localhost:5173");
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/login-processing/")
////                .queryParam("token", accessToken)
                .build().toUriString();
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//        String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue)
//                .orElse(("/"));

//        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("error", exception.getLocalizedMessage())
//                .build().toUriString();

//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        log.info("success handler, tokens:");
        log.info("현재 refreshToken: {}", refreshToken);
        tokenRepository.findAll().forEach(refreshToken1 -> {
            log.info("token: {}", refreshToken1.getRefreshToken());
        });


        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


}