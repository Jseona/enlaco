package com.example.enlaco.Controller;

import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
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



    @GetMapping("/list")
    public String list(String keyword, @RequestParam(defaultValue = "0") int page, Model model) throws Exception {
        if (page < 0) {
            page = 0; // 페이지 번호가 0보다 작을 경우 0으로 설정
        }
        Pageable pageable = PageRequest.of(page, 10); // PAGE_SIZE에는 한 페이지당 아이템 수가 들어가야 합니다.

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

        //s3 이미지 전달
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);

        return "/manager/list";
    }

}
