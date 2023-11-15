package com.example.enlaco.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/recipe")
public class RecipeController {
    @GetMapping("/detail")
    public String detail() throws Exception {
        return "/recipe/detail";
    }
    @GetMapping("insert")
    public String insertForm() throws Exception {
        return "/recipe/insert";
    }
    @PostMapping("insert")
    public String insertProc() throws Exception {
        return "redirect:/recipe/list";
    }
    @GetMapping("list")
    public String list() throws Exception {
        return "/recipe/list";
    }
    @GetMapping("modify")
    public String modifyForm() throws Exception {
        return "/recipe/modify";
    }
    @PostMapping("modify")
    public String modifyProc() throws Exception {
        return "redirect:/member/mypage";
    }
    @GetMapping("remove")
    public String remove() throws Exception {
        return "redirect:/member/mypage";
    }
}
