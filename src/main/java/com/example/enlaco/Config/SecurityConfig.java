package com.example.enlaco.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //1. 암호의 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //2. 커스텀 로그인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //매핑에 따른 접근권한 부여
        http.authorizeHttpRequests((auth)->{
            auth.antMatchers("/","/recipe/list","/recipe/detail","/member/login","/member/insert").permitAll();
            auth.antMatchers("/recipe/insert","/recipe/modify","/material/list","/material/detail","/material/insert","/material/modify","/material/remove").hasAnyRole("USER");
        });

        //로그인 처리에 대한 설정
        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/recipe/list")
                .usernameParameter("memail")
                .passwordParameter("mpwd")
                .failureUrl("/member/login/error");
        //페이지 변조방지 사용 안함.
        http.csrf().disable();

        //로그아웃에 대한 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");

        return http.build();
    }
}
