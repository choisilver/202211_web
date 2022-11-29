package com.example.spring03.dto;

import com.example.spring03.domain.Post;

import lombok.Data;

@Data

public class PostUpdateDto {
    
    // 필드 이름은 요청 파라미터와 같게.
    
    private Integer id;
    private String title;
    private String content;
    // DTO를 Entity 객체로 변환
    public Post toEntity() {
        return Post.builder().id(id).title(title).content(content).build();
    }
}
