package com.example.enlaco.Controller;

import com.example.enlaco.DTO.StorageDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/material")
public class MaterialController {
    @GetMapping("/detail")
    public String detail() throws Exception {
        return "/material/detail";
    }
    @GetMapping("/insert")
    public String insertForm() throws Exception {
        return "/material/insert";
    }
    @PostMapping("/insert")
    public String insertProc() throws Exception {
        return "redirect:/material/list";
    }
    @GetMapping("/list")
    public String list() throws Exception {
        return "/material/list";
    }
    @GetMapping("/modify")
    public String modifyForm() throws Exception {
        return "/material/modify";
    }
    @PostMapping("/modify")
    public String modifyProc() throws Exception {
        return "redirect:/material/list";
    }
    @GetMapping("/remove")
    public String remove() throws Exception {
        return "redirect:/material/list";
    }

}
