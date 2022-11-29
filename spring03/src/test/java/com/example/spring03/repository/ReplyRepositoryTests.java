package com.example.spring03.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring03.domain.Post;
import com.example.spring03.domain.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;
    
    @Autowired private PostRepository postRepository;
    
    @Test
    public void testFindBy() {
        Post post = postRepository.findById(1).get();
        Reply reply = Reply.builder().post(post).replyText("댓글 작성 test").writer("admin").build();
        
        log.info("save 전 reply= {} | {}", reply, reply.getCreatedTime());
        
        reply =  replyRepository.save(reply);
        log.info("save 후 reply= {} | {}", reply, reply.getCreatedTime());
        
        
        List<Reply> list = null;
        list = replyRepository.findAll();
        
        log.info("# of list = {}" , list.size());
        
        for(Reply r : list) {
            log.info(r.toString());
        }
    }
    
    
    // 댓글 insert -> post에 들어감 
    
}
