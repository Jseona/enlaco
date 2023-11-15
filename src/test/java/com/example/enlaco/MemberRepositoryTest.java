package com.example.enlaco;

import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    //Storage 테스트를 위한 테스트 회원 등록
    @Test
    public void member() {
        for (int i=1; i<=3; i++) {
            MemberEntity member = MemberEntity.builder()
                    .memail("sample" + i + "@gmail.com")
                    .mpwd("1234")
                    .mnick("닉네임" + i)
                    .mphone("010-1234-1234")
                    .mbirth("1993-11-15")
                    .build();

            memberRepository.save(member);
        }
    }
}
