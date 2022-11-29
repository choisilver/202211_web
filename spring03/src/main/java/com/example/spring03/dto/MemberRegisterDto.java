package com.example.spring03.dto;

import com.example.spring03.domain.Member;
import com.example.spring03.domain.MemberRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterDto {

    // 요청 파라미터 이름과 같게 필드들을 선언
    private String username;
    private String password;
    private String email;
    private String nickname;
    
    
    public Member toEntity() {
        return Member.builder().username(username).password(password).email(email).nickname(nickname).build().addRole(MemberRole.USER);
    }
    
}
