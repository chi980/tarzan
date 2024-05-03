package com.mjutarzan.tarzan.global.security.handler;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final JwtTokenUtil jwtUtil;


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
        String accessToken =  jwtUtil.createAccessToken(oAuth2User.getUserDTO().getUsername(), oAuth2User.getUserDTO().getRole());
        // 발급시 마다 저장..
        String refreshToken =  jwtUtil.createRefreshToken(oAuth2User.getUserDTO().getUsername(), oAuth2User.getUserDTO().getRole());



        response.addCookie(jwtUtil.createCookie("Authorization", accessToken));
        response.addCookie(jwtUtil.createCookie("refresh", refreshToken));
        response.sendRedirect("http://localhost:5173/");
    }


}