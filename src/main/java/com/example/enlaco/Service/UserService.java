package com.example.enlaco.Service;

import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Repository.MemberRepository;
import com.example.enlaco.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    //로그인시 필요한 값을 세션에 저장
    public void toSession(HttpSession session, String email) {
        MemberEntity member = getUserByEmail(email);

        if (member != null) {
            session.setAttribute("userEmail", member.getMemail());
            session.setAttribute("mid", member.getMid());
            session.setAttribute("mnickname", member.getMnick());
        }
    }
    public MemberEntity getUserByEmail(String email) {
        return userRepository.findByMemail(email).orElse(null);
    }
}
