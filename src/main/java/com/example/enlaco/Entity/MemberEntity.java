package com.example.enlaco.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "member")
@SequenceGenerator(
        name = "member_SEQ",
        sequenceName = "member_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_SEQ")
    private Integer mid;        //회원번호
    @Column(name = "memail", length = 100, unique = true, nullable = false)
    private String  memail;     //회원이메일
    @Column(name = "mpwd", length = 100)
    private String  mpwd;       //회원비번
    @Column(name = "mnick", length = 100, unique = true)
    private String  mnick;      //닉네임
    @Column(name = "mphone", length = 20)
    private String  mphone;     //폰번호
    @Column(name = "mbirth", length = 10)
    private String  mbirth;     //생년월일
}
