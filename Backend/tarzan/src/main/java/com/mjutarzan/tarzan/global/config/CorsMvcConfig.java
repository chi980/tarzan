package com.mjutarzan.tarzan.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://tazan.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) // ✅ 쿠키 전송 허용
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie", "Authorization"); // ✅ 쿠키, 토큰 등 헤더 노출
    }
}