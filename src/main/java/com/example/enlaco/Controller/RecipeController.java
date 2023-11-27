package com.example.enlaco.Controller;

import com.example.enlaco.DTO.CommentDTO;
import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Service.CommentService;
import com.example.enlaco.Service.MemberService;
import com.example.enlaco.Service.RecipeService;
import com.example.enlaco.Util.S3Uploader;
import jdk.dynalink.beans.StaticClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.Environment;
import org.jboss.jandex.Index;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;
import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final CommentService commentService;
    private final MemberService memberService;

    //S3 이미지 정보
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.region.static}")
    public String region;
    @Value("${imgUploadLocation}")
    public String folder;

    //상세페이지
    @GetMapping("/detail")
    public String detail(Principal principal, int rid, Model model) throws Exception {
        String writer = "";
        if (principal == null) {
            writer = "";
        } else {
            writer = principal.getName();
        }

        recipeService.viewcnt(rid);


        RecipeDTO recipeDTO = recipeService.detail(rid);

        List<CommentDTO> commentDTOS = commentService.list(rid);

        model.addAttribute("recipeDTO", recipeDTO);
        model.addAttribute("commentDTOS", commentDTOS);
        model.addAttribute("writer", writer);

        //S3 이미지정보전달
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);

        return "recipe/detail";
    }

    //입력
    @GetMapping("/insert")
    public String insertForm(Principal principal, Model model) throws Exception {
        String writer = principal.getName();
        int mid = memberService.findByMemail1(writer);
        RecipeDTO recipeDTO = new RecipeDTO();

        System.out.println("principal로 조회한 mid : " + mid);

        model.addAttribute("writer", writer);
        model.addAttribute("mid", mid);
        model.addAttribute("recipeDTO", recipeDTO);
        return "recipe/insert";
    }
    @PostMapping("/insert")
    public String insertProc( @Valid RecipeDTO recipeDTO,
                              BindingResult bindingResult,
                              @RequestParam("mid") int mid,
                             @RequestParam("image")MultipartFile multipartFile
                             ) throws Exception {
        if (bindingResult.hasErrors()) {
            return "recipe/insert";
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            recipeService.insert(mid, recipeDTO, multipartFile);
        } else {
            recipeService.insert(mid, recipeDTO, null); //파일이 없는 경우에도 처리
        }

        return "redirect:/recipe/list";
    }

    //목록
    @GetMapping("/list")
    public String list(@PageableDefault(page = 1) Pageable pageable,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       Model model, /*Principal principal,*/ HttpSession session) throws Exception {
        /*String email = principal.getName();
        String username =  email.substring(0,email.lastIndexOf('@'));
        session.setAttribute("username",username);*/
        Page<RecipeDTO> recipeDTOS = recipeService.list(keyword, pageable);

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

        model.addAttribute("keyword", keyword);

        //s3 이미지 전달
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);
//
        /*model.addAttribute("username",username);*/

        return "recipe/list";
    }

    //분류 리스트
    @GetMapping("/listClass")
    public String recipeClass(@RequestParam(value = "rtime", defaultValue = "") String rtime,
                              @RequestParam(value = "rclass", defaultValue = "") String rclass,
                              @PageableDefault(page = 1) Pageable pageable,
                              Model model) throws Exception {

        Page<RecipeDTO> recipeDTOS = recipeService.listClass(rtime, rclass, pageable);
        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber()/blockLimit)))-1) * blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<recipeDTOS.getTotalPages())? startPage+blockLimit-1:recipeDTOS.getTotalPages();

        int prevPage = pageable.getPageNumber()-1;
        int curPage = pageable.getPageNumber();
        int nextPage = pageable.getPageNumber()+1;
        int lastPage = pageable.getPageSize();

        model.addAttribute("recipeDTOS", recipeDTOS);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("curPage", curPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("lastPage", lastPage);

        model.addAttribute("rtime", rtime);
        model.addAttribute("rclass", rclass);

        return "recipe/listrclass";
    }

    //수정
    @GetMapping("/modify")
    public String modifyForm(Principal principal, int rid, Model model) throws Exception {
        String writer = principal.getName();
        int mid = memberService.findByMemail1(writer);

        RecipeDTO recipeDTO = recipeService.detail(rid);
        String select = recipeDTO.getRselect();
        System.out.println("recipeDTO에서 가져온 rselect : " + select);

        String list[] = select.split(",");
        for (int i=0; i<list.length; i++) {
            System.out.println(list[i]);
        }

        model.addAttribute("writer", writer);
        model.addAttribute("mid", mid);
        model.addAttribute("recipeDTO", recipeDTO);
        model.addAttribute("list", list);

        //s3 이미지 전달
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);

        return "recipe/modify";
    }
    @PostMapping("/modify")
    public String modifyProc(@Valid RecipeDTO recipeDTO,
                             BindingResult bindingResult,
                             Principal principal,
                             @RequestParam("mid") int mid,
                             MultipartFile imgFile,
                              Model model) throws Exception {
        String memail = principal.getName();
        if (bindingResult.hasErrors()) {
            return "recipe/modify";
        }
        model.addAttribute("mid", memail);
        recipeService.modify(recipeDTO, memail, imgFile);

        return "redirect:/member/mypage";
    }

    //삭제
    @GetMapping("/remove")
    public String remove(int rid) throws Exception {
        recipeService.remove(rid);
        return "redirect:/member/mypage";
    }
}
