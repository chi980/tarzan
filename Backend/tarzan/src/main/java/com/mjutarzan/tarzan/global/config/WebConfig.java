package com.mjutarzan.tarzan.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5173")  // 허용할 프론트엔드 도메인
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드
                        .allowedHeaders("*")  // 허용할 헤더
                        .allowCredentials(true);  // 인증 정보 허용 (옵션)
            }
        };
    }
}
