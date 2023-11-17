package com.example.enlaco.Service;

import com.example.enlaco.Constant.Role;
import com.example.enlaco.DTO.MemberDTO;
import com.example.enlaco.DTO.RecipeDTO;
import com.example.enlaco.Entity.MemberEntity;
import com.example.enlaco.Entity.RecipeEntity;
import com.example.enlaco.Repository.MemberRepository;
import com.example.enlaco.Repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final PasswordEncoder passwordEncoder;

    //로그인 처리
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        //이메일로 회원 조회
        MemberEntity memberEntity = memberRepository.findByMemail(memail);

        if (memberEntity == null) {
            throw new UsernameNotFoundException(memail);
        }

        //보안 인증에서 해당 데이터로 로그인 처리
        return User.builder()
                .username(memberEntity.getMemail())
                .password(memberEntity.getMpwd())
                .roles(memberEntity.getRole().toString())
                .build();
    }

    //회원가입 처리
    public void saveMember(MemberDTO memberDTO) throws Exception {
        //비밀번호 암호화 처리
        String password = passwordEncoder.encode(memberDTO.getMpwd());
        //MemberEntity에 저장
        MemberEntity memberEntity = MemberEntity.builder()
                .memail(memberDTO.getMemail())
                .mnick(memberDTO.getMnick())
                .mpwd(password)
                .mphone(memberDTO.getMphone())
                .mbirth(memberDTO.getMbirth())
                .role(Role.USER)
                .build();

        validateDuplicateMember(memberEntity);  //저장할 데이터를 중복체크(신규회원 등록시에만)
        memberRepository.save(memberEntity);
    }

    //이메일 중복 체크
    private void validateDuplicateMember(MemberEntity memberEntity) {
        //데이터베이스에서 조회
        MemberEntity findMember = memberRepository.findByMemail(memberEntity.getMemail());

        if (findMember != null) {  //이메일이 존재하면
            throw new IllegalStateException("이미 가입된 회원입니다.");  //오류발생
        }
    }

    //마이페이지조회
    public Page<RecipeDTO> myList(int mid, Pageable pageable) throws Exception {
        int curPage = pageable.getPageNumber()-1;
        int pageLimit = 10;

        Pageable newPage = PageRequest.of(curPage, pageLimit,
                Sort.by(Sort.Direction.DESC,"regDate"));

        Page<RecipeEntity> recipeEntities = memberRepository.myList(mid, newPage);

        Page<RecipeDTO> recipeDTOS = recipeEntities.map(data-> RecipeDTO.builder()
                .rid(data.getRid())
                .rimg(data.getRimg())
                .rmenu(data.getRmenu())
                .rcontent(data.getRcontent())
                .rwriter(data.getRwriter())
                .rclass(data.getRclass())
                .rtime(data.getRtime())
                .rselect(data.getRselect())
                .rviewcnt(data.getRviewcnt())
                .rgoodcnt(data.getRgoodcnt())
                .regDate(data.getRegDate())
                .modDate(data.getModDate())
                .mid(data.getMemberEntity().getMid())
                .build());

        return recipeDTOS;
    }


}
