//2023-11-15 정선아 : findByMid 메소드 생성
package com.example.enlaco.Repository;

import com.example.enlaco.Entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
    @Query(value = "SELECT * FROM Storage WHERE mid = :mid",
    nativeQuery = true)
    List<StorageEntity> findByMid(int mid);
}
