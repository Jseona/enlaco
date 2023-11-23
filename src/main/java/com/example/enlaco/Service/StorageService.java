package com.example.enlaco.Service;

import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.StorageEntity;
import com.example.enlaco.Repository.MemberRepository;
import com.example.enlaco.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StorageService {
    @Value("${imgLocation}")
    private String imgLocation;

    private final StorageRepository storageRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final FileService fileService;
    private final ModelMapper modelMapper = new ModelMapper();

    //삭제
    public void remove(int sid) throws Exception {
        StorageEntity read = storageRepository.findById(sid).orElseThrow();
        fileService.deleteFile(imgLocation, read.getSimg());

        storageRepository.deleteById(sid);
    }

    //개별조회
    public StorageDTO detail(Integer sid) throws Exception {
        StorageEntity storage = storageRepository.findById(sid).orElseThrow();

        StorageDTO storageDTO = modelMapper.map(storage, StorageDTO.class);

        return storageDTO;
    }

    //전체조회
    public List<StorageDTO> list(int mid) throws Exception {
        List<StorageEntity> storageEntities = storageRepository.findByMid(mid);

        List<StorageDTO> storageDTOS = Arrays.asList(
                modelMapper.map(storageEntities, StorageDTO[].class));

        return storageDTOS;
    }

    //삽입
    public void insert(int id, StorageDTO storageDTO, MultipartFile imgFile) throws Exception {
        Optional<MemberEntity> data = memberRepository.findById(id);
        MemberEntity member = data.orElseThrow();

        String originalFileName = imgFile.getOriginalFilename();
        String newFIleName = "";

        if (imgFile != null && !imgFile.isEmpty()) {
            newFIleName = fileService.uploadFile(imgLocation, originalFileName, imgFile.getBytes());
        }

        storageDTO.setSimg(newFIleName);

        StorageEntity storage = modelMapper.map(storageDTO, StorageEntity.class);
        storage.setMemberEntity(member);

        storageRepository.save(storage);
    }

    //수정
    public void modify(String memail, StorageDTO storageDTO, MultipartFile imgFile) throws Exception {
        Optional<StorageEntity> read = storageRepository.findById(storageDTO.getSid());
        StorageEntity storage = read.orElseThrow();

        Optional<MemberEntity> data = memberRepository.findById(memberService.findByMemail1(memail));
        MemberEntity member = data.orElseThrow();

        String deleteFile = storage.getSimg();

        String originalFileName = imgFile.getOriginalFilename();
        String newFileName = "";

        if (originalFileName.length() != 0) {
            if (deleteFile.length() != 0) {
                fileService.deleteFile(imgLocation, deleteFile);
            }

            newFileName = fileService.uploadFile(imgLocation,
                    originalFileName, imgFile.getBytes());
            storage.setSimg(newFileName);
        }

        /*storageDTO.setSid(storage.getSid());  //밑에서 Entity로 한번에 저장*/
        StorageEntity update = modelMapper.map(storageDTO, StorageEntity.class);
        update.setSid(storage.getSid());
        /*update.setSbuydate(storage.getSbuydate());  //구매날짜*/
        update.setSyutong(storage.getSyutong());
        update.setSimg(storage.getSimg());
        update.setSingre(storage.getSingre());
        /*update.setSkeep(storage.getSkeep());  //보관방법*/
        /*update.setSquan(storage.getSquan());  //수량*/
        update.setMemberEntity(member);

        storageRepository.save(update);
    }

    //디데이 구하기
    public long calculateDDay() {
        StorageEntity storage = new StorageEntity(); // StorageEntity 객체 초기화

        // StorageEntity에서 syutong 날짜 문자열 가져오기
        String syutongDateString = storage.getSyutong(); // getSyutong() 메서드를 통해 날짜 문자열 가져오기

        // 가져온 문자열을 LocalDate로 파싱
        LocalDate syutongDate = LocalDate.parse(syutongDateString);

        // 현재 날짜
        LocalDate currentDate = LocalDate.now();

        // 두 날짜 간의 차이 계산
        return ChronoUnit.DAYS.between(syutongDate, currentDate);
    }

}
