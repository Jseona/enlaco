package com.example.enlaco.Controller;

import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;

    //상세보기
    @GetMapping("/detail")
    public String detail() throws Exception {
        return "/storage/detail";
    }
    //입력창
    @GetMapping("/insert")
    public String insertForm() throws Exception {
        return "/storage/insert";
    }
    @PostMapping("/insert")
    public String insertProc() throws Exception {
        return "redirect:/storage/list";
    }
    //목록
    @GetMapping("/list")
    public String list(Model model) throws Exception {
        List<StorageDTO> storageDTOS = storageService.list();

        model.addAttribute("storageDTOS", storageDTOS);

        return "/storage/list";
    }
    //수정창
    @GetMapping("/modify")
    public String modifyForm() throws Exception {
        return "storage/modify";
    }
    @PostMapping("/modify")
    public String modifyProc() throws Exception {
        return "redirect:/storage/list";
    }
    //삭제
    @GetMapping("/remove")
    public String remove() throws Exception {
        return "redirect:/storage.list";
    }
}
