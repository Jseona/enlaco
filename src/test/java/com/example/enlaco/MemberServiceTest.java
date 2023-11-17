package com.example.enlaco;

import com.example.enlaco.DTO.MemberDTO;
import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void saveMemberTest() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemail("sample4@gmail.com");
        memberDTO.setMnick("닉네임4");
        memberDTO.setMpwd("1234");
        memberDTO.setMphone("010-1234-1234");
        memberDTO.setMbirth("2001-01-01");

        memberService.saveMember(memberDTO);
    }
}
