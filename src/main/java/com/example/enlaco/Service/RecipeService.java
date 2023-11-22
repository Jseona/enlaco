package com.example.enlaco.Service;

import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    @Value("c:/enlaco/image/")
    private String imgLocation;
    private final RecipeRepository recipeRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper = new ModelMapper();

    //삭제
    public void remove(int rid) throws Exception {
        recipeRepository.deleteById(rid);
    }
    //수정
    public void modify(RecipeDTO recipeDTO) throws Exception {
        int id = recipeDTO.getRid();
        Optional<RecipeEntity> read = recipeRepository.findById(id);
        RecipeEntity recipe = read.orElseThrow();

        RecipeEntity update = modelMapper.map(recipeDTO, RecipeEntity.class);
        update.setRid(recipe.getRid());

        recipeRepository.save(update);
    }
    //삽입
    public void insert(RecipeDTO recipeDTO, MultipartFile imgFile) throws Exception {
        String originalFileName = imgFile.getOriginalFilename();
        String newFileName = "";
        if (originalFileName != null) {
            newFileName = fileService.uploadFile(imgLocation,
                    originalFileName, imgFile.getBytes());
        }
        recipeDTO.setRimg(newFileName);

        RecipeEntity recipe = modelMapper.map(recipeDTO, RecipeEntity.class);

        recipeRepository.save(recipe);
    }
    //개별조회
    public RecipeDTO detail(int rid) throws Exception {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(rid);

        RecipeDTO recipeDTO = modelMapper.map(recipeEntity, RecipeDTO.class);

        return  recipeDTO;
    }
    //전체조회
    public Page<RecipeDTO> list(String keyword, Pageable pageable) throws Exception {
        int curPage = pageable.getPageNumber()-1;
        int pageLimit = 10;

        Pageable newPage = PageRequest.of(curPage, pageLimit,
                Sort.by(Sort.Direction.DESC,"rviewcnt"));

        Page<RecipeEntity> recipeEntities;

        if (keyword != null) {
            recipeEntities = recipeRepository.searchRecipe(keyword, newPage);
        } else {
            recipeEntities = recipeRepository.findAll(newPage);
        }
        /*
        if(rtime.equals("rtime1")) {
            recipeEntities = recipeRepository.searchRtimeRclass("rtime1", pageable);
        } else if (rtime.equals("rtime2")) {

        }

         */

        Page<RecipeDTO> recipeDTOS = recipeEntities.map(data-> RecipeDTO.builder()
                .rid(data.getRid())
                .rimg(data.getRimg())
                .rmenu(data.getRmenu())
                .rcontent(data.getRcontent())
                .rwriter(data.getRwriter())
                .rclass(data.getRclass())
                .rtime(data.getRtime())
                .rselect(data.getRselect())
                .rviewcnt(data.getRviewcnt())
                .rgoodcnt(data.getRgoodcnt())
                .regDate(data.getRegDate())
                .modDate(data.getModDate())
                .mid(data.getMemberEntity().getMid())
                .build());

        return recipeDTOS;
    }

    //리스트 조회
    public Page<RecipeDTO> listClass(String rtime, String rclass, Pageable pageable) throws Exception {
        int curPage = pageable.getPageNumber()-1;
        int pageLimit = 5;

        Pageable newPage = PageRequest.of(curPage, pageLimit,
                Sort.by(Sort.Direction.DESC,"rviewcnt"));

        Page<RecipeEntity> recipeEntities;

        if (rtime.equals("1") || rtime.equals("2") || rtime.equals("3")) {
            if (rtime.equals("1") && (rclass.equals("1") || rclass.equals("2") || rclass.equals("3") || rclass.equals("4") || rclass.equals("5") || rclass.equals("6"))) {
                recipeEntities = recipeRepository.searchRtimeRclass(rtime, rclass, newPage);
            } else if (rtime.equals("2") && (rclass.equals("1") || rclass.equals("2") || rclass.equals("3") || rclass.equals("4") || rclass.equals("5") || rclass.equals("6"))) {
                recipeEntities = recipeRepository.searchRtimeRclass(rtime, rclass, newPage);
            } else if (rtime.equals("3") && (rclass.equals("2") || rclass.equals("3") || rclass.equals("4") || rclass.equals("5") || rclass.equals("6"))) {
                recipeEntities = recipeRepository.searchRtimeRclass(rtime, rclass, newPage);
            } else {
                recipeEntities = recipeRepository.searchRtime(rtime, newPage);
            }
        } else {
            switch (rclass) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                    recipeEntities = recipeRepository.searchRclass(rclass, newPage);
                    break;
                default:
                    recipeEntities = recipeRepository.findAll(newPage);
                    break;
            }
        }


        Page<RecipeDTO> recipeDTOS = recipeEntities.map(data-> RecipeDTO.builder()
                .rid(data.getRid())
                .rimg(data.getRimg())
                .rmenu(data.getRmenu())
                .rcontent(data.getRcontent())
                .rwriter(data.getRwriter())
                .rclass(data.getRclass())
                .rtime(data.getRtime())
                .rselect(data.getRselect())
                .rviewcnt(data.getRviewcnt())
                .rgoodcnt(data.getRgoodcnt())
                .regDate(data.getRegDate())
                .modDate(data.getModDate())
                .mid(data.getMemberEntity().getMid())
                .build());

        return recipeDTOS;
    }

    //조회수
   public void viewcnt(int rid) throws Exception {
        recipeRepository.rviewcnt(rid);
    }
    //좋아요
    public void goodcnt(int rid) throws Exception {
         recipeRepository.rgoodcnt(rid);
    }
}
