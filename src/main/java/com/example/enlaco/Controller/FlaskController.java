package com.example.enlaco.Controller;

import com.example.enlaco.DTO.FlaskDTO;
import com.example.enlaco.Util.Flask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FlaskController {
    //S3 이미지 정보
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.region.static}")
    public String region;
    @Value("${imgUploadLocation}")
    public String folder;
    @Value("${tempFolder}")
    private String tempFolder;

    @Autowired
    private Flask flask;

    @GetMapping("/storage/image")
    public String image() throws Exception {
        return "/storage/image";
    }

    @PostMapping("/storage/result")
    public String result(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        FlaskDTO dto = flask.requestToFlask(file);

        model.addAttribute("dto", dto);
        model.addAttribute("tempFolder", tempFolder);
        return "/storage/result";
    }
}
