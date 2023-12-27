package com.example.enlaco;

import com.example.enlaco.Repository.CommentRepository;
import com.example.enlaco.Repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void findByRecipeIdTest() {
        Integer recipeId = 4;

    }

}
