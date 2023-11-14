package com.example.enlaco.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/insert")
    public String insertForm() throws Exception {
        return "/member/insert";
    }
    @PostMapping("/insert")
    public String insertProc() throws Exception {
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login() throws Exception {
        return "/member/login";
    }
}
