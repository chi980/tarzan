package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/api")
    @ResponseBody
    public String mainAPI(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        log.info("{}", customOAuth2User);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return "Main Controller : "+name;
    }
}