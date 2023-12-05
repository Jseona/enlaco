package com.example.enlaco.RecipeTest;

import com.example.enlaco.Repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeRepositoryTest {
    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void SeperateRselectTest() throws Exception {
        int rid = 1091;
        System.out.println(recipeRepository.SeperateRselect(rid).toString());
    }
}
