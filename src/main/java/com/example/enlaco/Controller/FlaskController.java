package com.example.enlaco.Controller;

import com.example.enlaco.DTO.FlaskDTO;
import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Service.StorageService;
import com.example.enlaco.Util.Flask;
import com.example.enlaco.Util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

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
    private final StorageService storageService;
    private final S3Uploader s3Uploader;

    @GetMapping("/storage/image")
    public String image() throws Exception {
        return "/storage/image";
    }

    @PostMapping("/storage/result")
    public String result(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        String imageName = s3Uploader.upload(file, tempFolder);
        System.out.println("imageName : " + imageName);
        FlaskDTO dto = flask.requestToFlask(file);
        StorageDTO storageDTO = new StorageDTO();

        model.addAttribute("dto", dto);
        model.addAttribute("tempFolder", tempFolder);
        model.addAttribute("storageDTO", storageDTO);
        model.addAttribute("imageName", imageName);
        return "/storage/result";
    }

    @PostMapping("/storage/AIinsert")
    public String AIinsert(@RequestParam("mid") String id,
                           @RequestParam("imageName") String name,
                           StorageDTO storageDTO) throws Exception {
        int mid = Integer.parseInt(id);
        storageService.aiInsert(mid, storageDTO, name);

        return "redirect:/storage/list";
    }
}
