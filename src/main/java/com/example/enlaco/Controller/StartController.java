package com.example.enlaco.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {
    @GetMapping("/")
    public String gyuindex() throws Exception {
        return "/gyuindex";
    }

}
