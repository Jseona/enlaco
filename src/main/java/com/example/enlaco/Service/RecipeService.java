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
    @Value("${imgLocation}")
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

        return recipeDTO;
    }
    //전체조회
    public Page<RecipeDTO> list(Pageable pageable) throws Exception {
        int curPage = pageable.getPageNumber()-1;
        int pageLimit = 30;

        Pageable newPage = PageRequest.of(curPage, pageLimit, Sort.by(Sort.Direction.DESC,"rviewcnt"));

        Page<RecipeEntity> recipeEntities = recipeRepository.findAll(newPage);

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
                .rRegDate(data.getRegDate())
                .rModDate(data.getModDate())
                .mid(data.getMemberEntity().getMid())
                .build());

        return recipeDTOS;
    }
    //조회수
   /*public void viewcnt(int rid) throws Exception {
        recipeRepository.rviewcnt(rid);
    }*/
    //좋아요
    public void goodcnt(int rid) throws Exception {
        recipeRepository.rgoodcnt(rid);
    }
}
