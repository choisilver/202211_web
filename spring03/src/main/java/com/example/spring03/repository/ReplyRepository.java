package com.example.spring03.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring03.domain.Reply;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    
    // 해당 포스트 번호에 달려 있는 모든 댓글 리스트를 아이디 내림차순으로 검색
    // select * from REPLIES where POST_ID = ? order by ID desc
    List<Reply> findByPostIdOrderByIdDesc(Integer postId);
    
    // JPQL 클래스라고 생각하기(Reply)
    @Query("select r from REPLIES r where r.post.id = :postId order by r.id desc")
    List<Reply> selectAllReplies(@Param(value = "postId") Integer postId);

}
