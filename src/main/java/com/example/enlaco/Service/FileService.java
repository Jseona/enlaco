package com.example.enlaco.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    //저장할 경로, 파일명, 데이터값 설정
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();  //랜덤 문자열 생성 -> 파일이름으로 사용

        String extension = originalFileName.substring(
                originalFileName.lastIndexOf(".")
        );  //확장자 문자열 분리

        String saveFileName = uuid.toString() + extension;

        String uploadFullUrl = uploadPath + saveFileName;

        //하드디스크에 파일 저장
        FileOutputStream fos = new FileOutputStream(uploadFullUrl);
        fos.write(fileData);
        fos.close();

        return saveFileName;
    }

    //파일삭제(실제 파일삭제, 수정시 기존 파일을 삭제하고 새로운 파일을 저장하기 위해 사용)
    public void deleteFile(String uploadPath, String fileName) throws Exception {
        String deleteFilename = uploadPath + fileName;

        File deleteFile = new File(deleteFilename);
        if (deleteFile.exists()) {  //삭제할 파일이 존재할 시에만 삭제 진행
            deleteFile.delete();
        }
    }
}
