package com.example.enlaco.Service;

import com.example.enlaco.DTO.CommentDTO;
import com.example.enlaco.Entity.CommentEntity;
import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.CommentRepository;
import com.example.enlaco.Repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    //댓글 삭제
    public void remove(Integer rid) throws Exception {
        commentRepository.deleteById(rid);
    }

    //댓글 수정
    public void modify(CommentDTO commentDTO) throws Exception {
        Integer cid = commentDTO.getCid();

        Optional<CommentEntity> read = commentRepository.findById(cid);
        CommentEntity commentEntity = read.orElseThrow();

        Optional<RecipeEntity> data = recipeRepository.findById(commentDTO.getRid());
        RecipeEntity recipeEntity = data.orElseThrow();

        CommentEntity update = modelMapper.map(commentDTO, CommentEntity.class);
        update.setCid(commentEntity.getCid());
        update.setRecipeEntity(recipeEntity);

        commentRepository.save(update);
    }

    //삽입
    public void create(Integer rid, CommentDTO commentDTO) throws Exception {
        Optional<RecipeEntity> data = recipeRepository.findById(rid);
        RecipeEntity recipeEntity = data.orElseThrow();

        CommentEntity commentEntity = modelMapper.map(commentDTO, CommentEntity.class);
        commentEntity.setRecipeEntity(recipeEntity);

        commentRepository.save(commentEntity);

    }

    //전체조회
    public List<CommentDTO> list(Integer rid) throws Exception {
        List<CommentEntity> commentEntities = commentRepository.findByRid(rid);
        List<CommentDTO> commentDTOS = Arrays.asList(modelMapper.map(commentEntities, CommentDTO[].class));

        return commentDTOS;
    }

}
