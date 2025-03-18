package com.mjutarzan.tarzan.global.jwt;


import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.domain.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("현재 url: {}", request.getRequestURI());

        jwtTokenProvider.resolveToken(request)
                .filter(jwtTokenProvider::validateToken)  // validateToken이 true일 경우만 진행
                .ifPresent(token -> jwtTokenProvider.getEmail(token)  // 유효한 토큰에서 이메일을 추출
                        .ifPresent(email -> customUserDetailsService.loadUserByEmail(email)
                                .ifPresent(this::saveAuthentication)));  // 이메일로 사용자 인증

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(CustomUserDetails customUserDetails) {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(),
                            authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}