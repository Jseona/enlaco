package com.example.enlaco;

import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.StorageEntity;
import com.example.enlaco.Repository.StorageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StorageRepositoryTest {
    @Autowired
    private StorageRepository storageRepository;

    //findByMemberEntity를 테스트하기 위한 더미데이터 생성
    @Test
    public void insert() {
        for (int i=1; i<=50; i++) {
            MemberEntity member = MemberEntity.builder()
                    .mid((i % 3)+1)
                    .build();
            StorageEntity storage = StorageEntity.builder()
                    .singre("식재료" + i)
                    .sBuyDate("2023-11-01")
                    .sYutong("2023-12-31")
                    .squan(i % 10 + 1)
                    .skeep(String.valueOf((i % 3)+1))
                    .memberEntity(member)
                    .build();
            storageRepository.save(storage);
        }
    }

    //findByMid Test
    @Test
    public void memberEntityTest() {
        int mid = 1;

        List<StorageEntity> storageEntities = storageRepository.findByMid(mid);
        System.out.println(storageEntities.toString());
    }
}
