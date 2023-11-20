package com.example.enlaco.Controller;

import com.example.enlaco.DTO.MemberDTO;
import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Service.MemberService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final RecipeService recipeService;

    @GetMapping("/insert")
    public String insertForm(Model model) throws Exception {
        MemberDTO memberDTO = new MemberDTO();

        model.addAttribute("memberDTO", memberDTO);
        return "member/insert";
    }
    @PostMapping("/insert")
    public String insertProc(@Valid MemberDTO memberDTO,
                             BindingResult bindingResult,
                             int mid,
                             HttpServletResponse response,
                             RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            return "member/insert";
        }

        try {
            memberService.saveMember(memberDTO);
            redirectAttributes.addAttribute("errorMessage", "가입을 축하합니다.");

            /*Cookie cookie = new Cookie("mid", String.valueOf(mid));
            response.addCookie(cookie);*/

        } catch (IllegalStateException e) {
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
            return "member/insert";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() throws Exception {
        return "member/login";
    }
    @GetMapping("/login/error")
    public String loginError(Model model) throws Exception {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요.");
        return "member/login";
    }

    @GetMapping("/mypage")
    public String mypage(@PageableDefault(page = 1) Pageable pageable,
                         Model model) throws Exception {
        int mid = 1; //에러 방지를 위한 임의의 값 입력
        Page<RecipeDTO> recipeDTOS = memberService.myList(mid, pageable);

        //페이지 정보
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit)))-1) * blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<recipeDTOS.getTotalPages())? startPage+blockLimit-1:recipeDTOS.getTotalPages();

        int prevPage = recipeDTOS.getNumber();
        int curPage = recipeDTOS.getNumber()+1;
        int nextPage = recipeDTOS.getNumber()+2;
        int lastPage = recipeDTOS.getTotalPages();

        model.addAttribute("recipeDTOS", recipeDTOS);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("curPage", curPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("lastPage", lastPage);

        return "member/mypage";
    }

    @GetMapping("/myrecipedetail")
    public String myrecipedetail() throws Exception {
        return "member/myrecipedetail";
    }
}
