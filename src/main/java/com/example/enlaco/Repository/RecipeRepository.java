package com.example.enlaco.Repository;

import com.example.enlaco.Entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Integer> {
    @Modifying
    @Query(value = "UPDATE RecipeEntity u SET  u.rviewcnt=u.rviewcnt+1 WHERE u.rid=:rid",
    nativeQuery = true)
    public void rviewcnt(@Param("rid") Integer rid);

    @Modifying
    @Query(value = "UPDATE RecipeEntity u SET u.rgoodcnt=u.rgoodcnt+1 WHERE u.rid=:rid")
    public void rgoodcnt(int rid);
}
