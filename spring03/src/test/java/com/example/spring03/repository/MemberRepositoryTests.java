package com.example.spring03.repository;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.FetchType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring03.domain.Member;
import com.example.spring03.domain.MemberRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberRepositoryTests {
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Test
    public void test() {
        Assertions.assertNotNull(memberRepository);
        
        // 일반 사용자(USER 권한만 갖는 사용자)
        Member user1 = Member.builder()
                .username("user1").password("1111").email("user1@test.com")
                .nickname("사용자1")
                .build();
        user1.addRole(MemberRole.USER);
        memberRepository.save(user1); // DB table에 insert
        
    }

    @Test
    public void test2() {
        // USER + ADMIN 권한을 갖는 회원 등록
        Member admin1 = Member.builder()
                .username("admin1").password("admin1").email("admin1@itwill.co.kr")
                .nickname("관리자1")
                .build();
        admin1.addRole(MemberRole.USER).addRole(MemberRole.ADMIN);
        memberRepository.save(admin1);
    }
    
//    @Test
    @Transactional // roles 컬럼이 FetchType.LAZY로 설정되어 있기 때문.
    public void test3() {
        // Members 테이블 전체 검색
        List<Member> list = memberRepository.findAll();
        for (Member m : list) {
            log.info(m.toString());
        }
    }
    
//    @Test
    public void test4() {
        // username이 일치하는 사용자 정보 찾기
        try {
            Member m = memberRepository.findByUsername("TEST").get();
            log.info("일치하는 사용자 있음: {}", m);
        } catch (NoSuchElementException e) {
            log.info("일치하는 사용자 없음.");
        }
    }
    
}