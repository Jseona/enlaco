package com.example.enlaco.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/material")
public class MaterialController {
    @GetMapping("/detail")
    public String detail() throws Exception {
        return "/material/detail";
    }

}
