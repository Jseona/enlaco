package com.example.enlaco.Controller;

import com.example.enlaco.DTO.CommentDTO;
import com.example.enlaco.Service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/register")
    public String registerProc(int rid, CommentDTO commentDTO, RedirectAttributes redirectAttributes) throws Exception {
        commentService.create(rid, commentDTO);

        redirectAttributes.addAttribute("id", rid);
        return "redirect:/recipe/detail";
    }

    @GetMapping("/comment/remove")
    public String removeProc(int rid, int cid, RedirectAttributes redirectAttributes) throws Exception {
        commentService.remove(cid);

        redirectAttributes.addAttribute("id", rid);
        return "redirect:/recipe/detail";
    }
}
