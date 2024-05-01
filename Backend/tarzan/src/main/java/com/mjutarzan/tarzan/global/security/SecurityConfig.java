package com.mjutarzan.tarzan.global.security;

import com.mjutarzan.tarzan.domain.user.service.UserService;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                // token 사용하기 때문에 csrf disable
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .requestMatchers("/swagger-resources/**","/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 관련 경로 허용
                .requestMatchers("/error").permitAll() // error
                .requestMatchers("/api/oauth/authenticate").permitAll() // 로그인 api
                .requestMatchers("/api/oauth/signup").permitAll() // 회원가입 api
                .requestMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and().build();
    }
}