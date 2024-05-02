package com.mjutarzan.tarzan.domain.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/api")
    @ResponseBody
    public String mainAPI() {

        return "main route";
    }
}