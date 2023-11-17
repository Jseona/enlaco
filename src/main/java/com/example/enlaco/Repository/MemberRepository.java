package com.example.enlaco.Repository;

import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.RecipeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    //로그인 시 이메일로 조회
    MemberEntity findByMemail(String memail);

    //레시피에서 회원 별로 뽑아내기
    @Query(value = "SELECT a FROM RecipeEntity a LEFT JOIN MemberEntity b ON a.memberEntity.mid=b.mid")
    Page<RecipeEntity> myList(Integer mid, Pageable pageable);
}
