package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.domain.member.entity.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MyController {

    @GetMapping("/my")
    @ResponseBody
    public String myAPI(@AuthenticationPrincipal CustomOAuth2User user) {
        log.info("username: {}", user);

        return "my route";
    }
}