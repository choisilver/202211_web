package com.example.spring03.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring03.domain.Post;
import com.example.spring03.dto.PostCreateDto;
import com.example.spring03.dto.PostUpdateDto;
import com.example.spring03.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service // 스프링 컨텍스트에 Service 컴포넌트로 등록.
@RequiredArgsConstructor // final로 선언된 필드를 초기화하는 생성자.
public class PostService {
    
    private final PostRepository postRepository; // 생성자에 의한 의존성 주입.
    
    @Transactional(readOnly = true)  // 검색된 데이터를 변경하지 않음. select 속도가 빨라짐.
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByIdDesc();
    }

    public Post create(PostCreateDto dto) {
        log.info("create(dto) = {}",dto);
        Post entity = postRepository.save(dto.toEntity());
        
        return entity;
    }

    @Transactional(readOnly = true)
    public Post read(Integer id) {
        log.info("read(id={})", id);
        
        return postRepository.findById(id).get();
    }

    public Integer delete(Integer id) {
        log.info("delete(id={})", id);
        postRepository.deleteById(id); // return이 없음. void임
        
        
        return id;
    }
    
    @Transactional // readOnly = false 디폴트 자동으로 변경됨??!
    public Integer update(PostUpdateDto dto) {
        log.info("update(dto ={})",dto);
        
        
        // 메서드에 @Transactional 애너테이션을 사용하고,
        // 수정하기 전의 엔터티 객체를 검색 한 후에
        // 검색된 엔터티 객체를 수정하면
        // 메서드가 종료될 때 데이터베이스 테이블에 자동으로 update SQL이 실행됨
        // PostRepository의 save() 메서드를 명시적으로 호출 안함.
        Post entity = postRepository.findById(dto.getId()).get();
        entity.update(dto.getTitle(),dto.getContent());
        
        return entity.getId();
    }

    public List<Post> search(String type, String keyword) {
        log.info("search(type={}, keyword={})", type, keyword);
        
        List<Post> list =null;
        
        switch(type) {
        case "t":
            list = postRepository.findByTitleIgnoreCaseContainingOrderByIdDesc(keyword);
            break;
        case "c":
            list = postRepository.findByContentIgnoreCaseContainingOrderByIdDesc(keyword);
            break;
        case "tc":
            list = postRepository.searchByKeyword(keyword);
            break;
        case "a":
            list = postRepository.findByAuthorIgnoreCaseContainingOrderByIdDesc(keyword);
            break;
            
        
        }
        
        
        
        
        return list;
    }

}
