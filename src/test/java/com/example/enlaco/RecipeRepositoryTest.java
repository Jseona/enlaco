package com.example.enlaco;

import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;

    //테스트 작업을 위한 더미 데이터 생성
    @Test
    public void insert() {
        for (int i=1; i<=10; i++) {
            RecipeEntity recipe = RecipeEntity.builder()
                    .rmenu("레시피" + i)
                    .rcontent("레시피 상세내용" + i)
                    .rwriter("닉네임" + (i%3)+1)
                    .rclass(String.valueOf(i))
                    .rselect("감자,고구마,당근")
                    .rviewcnt(i)
                    .rgoodcnt(i)
                    .build();
            recipeRepository.save(recipe);
        }
    }

    //조회수 테스트
    /*@Test
    public void viewcntTest() {
        recipeRepository.rviewcnt(3);
    }*/
}
