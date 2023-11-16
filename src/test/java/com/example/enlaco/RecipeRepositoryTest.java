package com.example.enlaco;

import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;

    //페이징 테스트를 위한 추가 데이터 삽입
    @Test
    public void insert() {
        for (int i=81; i<=100; i++) {
            MemberEntity member = MemberEntity.builder()
                    .mid(i%3+1)
                    .build();
            RecipeEntity data = RecipeEntity.builder()
                    .rmenu("레시피" + i)
                    .rcontent("레시피 상세내용" + i)
                    .rwriter("닉네임" + (i%3+1))
                    .rclass(String.valueOf(i%10+1))
                    .rselect("감자,토마토")
                    .rviewcnt(0)
                    .rgoodcnt(0)
                    .memberEntity(member)
                    .build();

            recipeRepository.save(data);
        }
    }
}
