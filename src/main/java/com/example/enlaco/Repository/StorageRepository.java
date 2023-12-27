//2023-11-15 정선아 : findByMid 메소드 생성
package com.example.enlaco.Repository;

import com.example.enlaco.DTO.StorageDTO;
import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
    //Mid로 조회해서 목록으로 가져오기
    @Query(value = "SELECT * FROM Storage WHERE mid=:mid", nativeQuery = true)
    List<StorageEntity> findByMid(@Param("mid") Integer mid);

    @Query(value = "SELECT * FROM StorageEntity WHERE memail=:memail", nativeQuery = true)
    StorageEntity findByMemail1(String memail);

}


