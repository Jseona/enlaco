package com.example.enlaco.Service;

import com.example.enlaco.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //보안 외부라이브러리 적용시 커스텀 로그인 작업 해주기
}
