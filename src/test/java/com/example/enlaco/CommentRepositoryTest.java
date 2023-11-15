package com.example.enlaco;

import com.example.enlaco.Entity.CommentEntity;
import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    //테스트를 위한 더미 데이터 생성
    @Test
    public void insert() {
        for (int i=1; i<=10; i++) {
            RecipeEntity recipe = RecipeEntity.builder()
                    .rid(i)
                    .build();
            CommentEntity comment = CommentEntity.builder()
                    .cbody("댓글 더미데이터" + i)
                    .cwriter("닉네임" + (i%3)+1)
                    .recipeEntity(recipe)
                    .build();
            commentRepository.save(comment);
        }
    }

    //findByRid 테스트
    @Test
    public void findByRidTest() {
        int id = 3;

        List<CommentEntity> commentEntities = commentRepository.findByRid(id);
        System.out.println(commentEntities.toString());
    }
}
