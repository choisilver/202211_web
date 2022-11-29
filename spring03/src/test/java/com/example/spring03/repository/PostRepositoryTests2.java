package com.example.spring03.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring03.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostRepositoryTests2 {

    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void testUpdate() {
        // DB table update: 
        // (1) id 수정할 엔터티 검색
        
        Post entity = postRepository.findById(1).get();  // fingById는 Optional<Post>를 리턴함. null일 수도 있고 아닐 수도 있음!
        log.info("수정 전:{} | {} | {}", entity , entity.getCreatedTime(), entity.getModifiedTime());
        
        // (2) 엔터티를 수정
        entity.update("update 테스트 제목", "내용 - Spring Data JPA 사용");
        log.info("수정 후: {} | {} | {}", entity , entity.getCreatedTime(), entity.getModifiedTime());
        
        // (3) 수정한 엔터티를 postRepository.save(entity) 메서드 호출
        entity = postRepository.save(entity); // DB에 수정 내용 반영(select -> update -> commit)
        
        log.info("save 후: {} | {} | {}", entity , entity.getCreatedTime(), entity.getModifiedTime());
        
        
        
    }
    
    @Test
    public void testDelete() {
        
        
        // delete from POSTS where id = 21  이런 sql문장을 실행
        postRepository.deleteById(21);  // select -> delete -> commit
        
        
    }
    
    
}
