package com.example.enlaco.Service;

import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Entity.StorageEntity;
import com.example.enlaco.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StorageService {
    @Value("${imgLocation}")
    private String imgLocation;

    private final StorageRepository storageRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper = new ModelMapper();

    //삭제
    public void remove(int sid) throws Exception {
        StorageEntity read = storageRepository.findById(sid).orElseThrow();
        fileService.deleteFile(imgLocation, read.getSimg());

        storageRepository.deleteById(sid);
    }
    //개별조회
    public StorageDTO detail(int sid) throws Exception {
        StorageEntity storage = storageRepository.findById(sid).orElseThrow();

        StorageDTO storageDTO = modelMapper.map(storage, StorageDTO.class);

        return storageDTO;
    }
    //전체조회
    public List<StorageDTO> list() throws Exception {
        List<StorageEntity> storageEntities = storageRepository.findAll();

        List<StorageDTO> storageDTOS = Arrays.asList(
                modelMapper.map(storageEntities, StorageDTO[].class));

        return storageDTOS;
    }
    //삽입
    public void insert(StorageDTO storageDTO, MultipartFile imgFile) throws Exception {
        String originalFileName = imgFile.getOriginalFilename();
        String newFIleName = "";
        if (originalFileName != null) { //기존 파일이 존재하면
            newFIleName = fileService.uploadFile(imgLocation,
                    originalFileName, imgFile.getBytes());  //새로운 이미지
        }
        storageDTO.setSimg(newFIleName);

        StorageEntity storage = modelMapper.map(storageDTO, StorageEntity.class);

        storageRepository.save(storage);
    }
    //수정
    public void modify(StorageDTO storageDTO, MultipartFile imgFile) throws Exception {
        StorageEntity storage = storageRepository.findById(storageDTO.getSid()).orElseThrow();
        String deleteFile = storage.getSimg();

        String originalFileName = imgFile.getOriginalFilename();
        String newFileName = "";

        if (originalFileName.length() != 0) {
            if (deleteFile.length() != 0) {
                fileService.deleteFile(imgLocation, deleteFile);
            }

            newFileName = fileService.uploadFile(imgLocation,
                    originalFileName, imgFile.getBytes());
            storageDTO.setSimg(newFileName);
        }

        storageDTO.setSid(storage.getSid());
        StorageEntity storageEntity = modelMapper.map(storageDTO, StorageEntity.class);

        storageRepository.save(storageEntity);
    }
}
