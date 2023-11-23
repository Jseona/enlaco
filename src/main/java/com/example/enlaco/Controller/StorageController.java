package com.example.enlaco.Controller;

import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;

    //상세보기
    @GetMapping("/detail")
    public String detail(int sid, Model model) throws Exception {
        StorageDTO storageDTO = storageService.detail(sid);

        model.addAttribute("storageDTO", storageDTO);

        return "/storage/detail";
    }
    //입력창
    @GetMapping("/insert")
    public String insertForm(Model model) throws Exception {
        StorageDTO storageDTO = new StorageDTO();
        model.addAttribute("storageDTO", storageDTO);

        return "storage/insert";
    }
    @PostMapping("/insert")
    public String insertProc(@Valid StorageDTO storageDTO, BindingResult bindingResult,
                             @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) throws Exception {
        if (bindingResult.hasErrors()) {
            return "storage/insert";
        }

        if (imgFile != null && !imgFile.isEmpty()) {
            storageService.insert(storageDTO, imgFile);
        } else {
            storageService.insert(storageDTO, null); // 파일이 없는 경우에도 처리 가능하도록 null 전달
        }

        return "redirect:/storage/list";
    }

    //목록
    @GetMapping("/list")
    public String list(Model model) {

        try {
            List<StorageDTO> storageDTOS = storageService.list();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();

            for (StorageDTO storageDTO : storageDTOS) {
                String syutong = storageDTO.getSyutong();

                if (syutong == null || syutong.isEmpty()) {
                    String dDay = "D+999";
                    storageDTO.setDDay(dDay);

                    // 오류 처리 또는 기본값 설정 등을 수행
                    // 예: throw new IllegalArgumentException("날짜 문자열이 비어있습니다.");
                    // 또는 기본값을 설정하거나 다른 처리를 수행할 수 있음
                    continue; // 빈 문자열이면 다음 반복으로 건너뜀
                }

                LocalDate endDate = LocalDate.parse(storageDTO.getSyutong(), formatter);
                long daysUntilEnd = ChronoUnit.DAYS.between(now, endDate);
                String dDay;
                if (now.isBefore(endDate)) {
                    dDay = "D-" + daysUntilEnd;
                } else if (now.equals(endDate)) {
                    dDay = "D-DAY";
                } else {
                    dDay = "D+" + Math.abs(daysUntilEnd);
                    String dDayOver = "유통기한 지남";

                    storageDTO.setDDay(dDay); // StorageDTO에 D-Day 값을 저장
                    storageDTO.setDDayOver(dDayOver);
                    continue;
                }
                storageDTO.setDDay(dDay);
            }

            model.addAttribute("storageDTOS", storageDTOS);

        } catch (Exception e) {
            log.error("Error occurred while processing storage list: {}", e.getMessage());
            // 예외 처리 로직 추가

        }

        return "/storage/list";
    }


    //수정창
    @GetMapping("/modify")
    public String modifyForm(int sid, Model model) throws Exception {
        StorageDTO storageDTO = storageService.detail(sid);

        model.addAttribute("storageDTO", storageDTO);
        return "storage/modify";
    }
    @PostMapping("/modify")
    public String modifyProc(StorageDTO storageDTO, MultipartFile imgFile,
                             Model model, RedirectAttributes redirectAttributes) throws Exception {
        storageService.modify(storageDTO, imgFile);
        return "redirect:/storage/list";
    }
    //삭제
    @GetMapping("/remove")
    public String remove(int sid, Model model) throws Exception {
        storageService.remove(sid);
        return "redirect:/storage/list";
    }
}
