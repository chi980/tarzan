package com.mjutarzan.tarzan.domain.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MainController {

//    @GetMapping("/api")
//    @ResponseBody
//    public String mainAPI(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
//        log.info("{}", customOAuth2User);
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        return "Main Controller : "+name;
//    }

    @GetMapping("/api/test")
    @ResponseBody
    public String testApi(@ModelAttribute  MyPageable mypage){
        log.info("{}", mypage);
        return "ok";
    }
}