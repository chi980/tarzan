package com.mjutarzan.tarzan.domain.user.api;

import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.global.oauth2.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @GetMapping("/user")
    @ResponseBody
    public String myAPI(@AuthenticationPrincipal UserDto userDto) {
        log.info("myAPI 성공!");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) principal;
            String username = customOAuth2User.getName();
            String customField = customOAuth2User.getUsername();
            return "Username: " + username + ", Custom Field: " + customField;
        }else{
            log.info("{}",principal);
        }
        log.info("{}",userDto);
        return "test";
    }
}
