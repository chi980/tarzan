package com.mjutarzan.tarzan.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjutarzan.tarzan.domain.member.service.CustomOAuth2UserService;
import com.mjutarzan.tarzan.global.security.handler.CustomSuccessHandler;
import com.mjutarzan.tarzan.global.security.jwt.JwtExceptionFilter;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenFilter;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import com.mjutarzan.tarzan.global.security.token.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig{

    @Value("${spring.jwt.secret}")
    private String secretKey;


    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //HTTP Basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());
        
        //cors 활성화
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll() // test
                        .requestMatchers("/api/oauth/token/**").permitAll()
                        .anyRequest().authenticated());

        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        // 로그인 된 유저의 정보를 가져온다.
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        // 가져온 유저의 정보를 customOAuth2UserService가 처리
                        .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );


        // JwtExceptionFilter -> JwtFilter 추가 [ JwtFilter에서 예외 throw할 시 JwtExceptionFilter로 감 ]
        http
                .addFilterBefore(new JwtTokenFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(new ObjectMapper()), JwtTokenFilter.class);

//        http
//                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshTokenRepository), LogoutFilter.class);



        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors(cors -> cors.disable())
//                // token 사용하기 때문에 csrf disable
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
//                .requestMatchers("/swagger-resources/**","/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 관련 경로 허용
//                .requestMatchers("/error").permitAll() // error
////                .requestMatchers("/api/oauth/authenticate").permitAll() // 로그인 api
////                .requestMatchers("/api/oauth/signup").permitAll() // 회원가입 api
//                .requestMatchers("/api/oauth/**").permitAll()
//                .requestMatchers("/favicon.ico").permitAll()
//
//                .anyRequest().authenticated()
//                .and().build();
//    }
}