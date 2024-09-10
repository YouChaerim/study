package com.bookstore.bookstore_dto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 메인페이지 (로그인, 회원가입)
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
