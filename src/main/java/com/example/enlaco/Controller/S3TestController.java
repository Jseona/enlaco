package com.example.enlaco.Controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.enlaco.Util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class S3TestController {
    //이미지 출력 테스트
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.region.static}")
    public String region;
    @Value("${imgUploadLocation}")
    public String folder;

    private final AmazonS3Client amazonS3Client;

    private final S3Uploader s3Uploader;
    @GetMapping("/images")
    public String test(Model model) throws IOException {
        //이미지 출력 테스트
        model.addAttribute("bucket", bucket);
        model.addAttribute("region", region);
        model.addAttribute("folder", folder);

        URL url = amazonS3Client.getUrl(bucket,"989ec373-5396-4a87-bcfa-dbc88d84c860운동화.jpg");
        System.out.println(url);
        return "test";
    }

    @PostMapping("/images")
    public String upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "static");
        return "redirect:/images";
    }
}
