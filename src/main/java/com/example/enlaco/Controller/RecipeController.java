package com.example.enlaco.Controller;

import com.example.enlaco.DTO.CommentDTO;
import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Service.CommentService;
import com.example.enlaco.Service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final CommentService commentService;

    //상세페이지
    @GetMapping("/detail")
    public String detail(int rid, Model model) throws Exception {
        recipeService.viewcnt(rid);

        RecipeDTO recipeDTO = recipeService.detail(rid);

        List<CommentDTO> commentDTOS = commentService.list(rid);

        model.addAttribute("recipeDTO", recipeDTO);
        model.addAttribute("commentDTOS", commentDTOS);
        return "recipe/detail";
    }

    //입력
    @GetMapping("insert")
    public String insertForm(Model model) throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();

        model.addAttribute("recipeDTO", recipeDTO);
        return "recipe/insert";
    }
    @PostMapping("insert")
    public String insertProc(@Valid RecipeDTO recipeDTO,
                             MultipartFile imgFile,
                             BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "recipe/insert";
        }

        recipeService.insert(recipeDTO, imgFile);

        return "redirect:/recipe/list";
    }

    //목록
    @GetMapping("list")
    public String list(@PageableDefault(page = 1) Pageable pageable, Model model) throws Exception {
        Page<RecipeDTO> recipeDTOS = recipeService.list(pageable);

        //페이지 정보
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit)))-1) * blockLimit+1;
        int endPage = Math.min(startPage+blockLimit-1, recipeDTOS.getTotalPages());

        int prevPage = recipeDTOS.getNumber();
        int curPage = recipeDTOS.getNumber()+1;
        int nextPage = recipeDTOS.getNumber()+2;
        int lastPage = recipeDTOS.getTotalPages()-1;

        model.addAttribute("recipeDTOS", recipeDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("curPage", curPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("lastPage", lastPage);

        return "recipe/list";
    }

    //수정
    @GetMapping("modify")
    public String modifyForm(int rid, Model model) throws Exception {
        RecipeDTO recipeDTO = recipeService.detail(rid);

        model.addAttribute("recipeDTO", recipeDTO);
        return "recipe/modify";
    }
    @PostMapping("modify")
    public String modifyProc(@Valid RecipeDTO recipeDTO, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "recipe/modify";
        }
        recipeService.modify(recipeDTO);

        return "redirect:/member/mypage";
    }

    //삭제
    @GetMapping("remove")
    public String remove(int rid) throws Exception {
        recipeService.remove(rid);
        return "redirect:/member/mypage";
    }
}
