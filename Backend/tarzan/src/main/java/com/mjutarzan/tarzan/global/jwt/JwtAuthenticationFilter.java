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
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    private static final Set<String> NO_CHECK_URLS = Set.of(
            "/favicon.ico",
//            "/login",
            "/oauth2/authorization", "/api/auth",
            "/api/test", "/api/data");


    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("현재 url: {}", request.getRequestURI());
        boolean skipFilter = NO_CHECK_URLS.stream().anyMatch(request.getRequestURI()::startsWith);
        if (skipFilter) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 토큰을 요청에서 추출
            log.info("JWT 토큰을 요청에서 추출");

            Optional<String> tokenOpt = jwtTokenProvider.resolveToken(request);
            log.info("jwt token: {}", tokenOpt);

            // 토큰이 존재하고 유효한지 확인
            log.info("// 토큰이 존재하고 유효한지 확인");

            if (tokenOpt.isPresent() && jwtTokenProvider.validateToken(tokenOpt.get())) {
                // 유효한 토큰에서 이메일을 추출
                log.info("유효한 토큰에서 이메일을 추출");

                String email = jwtTokenProvider.getEmailFromAccessToken(tokenOpt.get());
                log.info("email: {}", email);
                Optional<CustomUserDetails> customUserDetails = customUserDetailsService.loadUserByEmail(email);
                if(customUserDetails.isPresent()){
                    log.info("customUserDetails가 있습니다.");
                    this.saveAuthentication(customUserDetails.get());
                }else{
                    handleErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "USER not found");  // 사용자 미발견 시 에러 응답
                    return;
                }

            } else {
                handleErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");  // 토큰 유효하지 않거나 없음
                return;
            }
        } catch (Exception e) {
            handleErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "재로그인이 필요합니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);  // 에러 응답 처리
    }

    private void saveAuthentication(CustomUserDetails customUserDetails) {
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(),
                            authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}