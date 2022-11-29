package com.example.spring03.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring03.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostSearchTests {
    
    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void test() {
        String keyword ="es";
        // like 검색에서 "%"를 사용하지 않아도 JPA에서 자동으로 처리해줌.
        
        List<Post> list = postRepository.searchByKeyword(keyword);
        for(Post p : list) {
            Assertions.assertTrue(p.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || p.getContent().toLowerCase().contains(keyword.toLowerCase()));
            log.info(p.toString());
        }
        
        
    }
    
    
    
}
