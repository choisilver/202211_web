package com.example.spring03.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.spring03.domain.Member;
import com.example.spring03.dto.MemberRegisterDto;
import com.example.spring03.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    
    public String checkUsername(String username) {
        log.info("checkUsername (username={})",username);
        
        Optional<Member> result = memberRepository.findByUsername(username);
        if (result.isPresent()) { // 동일한 username이 있는 경우
            return "nok";
            
        } else { // 동일한 username이 없는 경우
            return "ok";
        }
        
    }

    public Member registerMember(MemberRegisterDto dto) {
        log.info("registerMember(dto={})", dto);
        
        Member entity = memberRepository.save(dto.toEntity()); // 저장이 끝남
        log.info("entity = {}" , entity);
        
        return entity;
    }
    
}
