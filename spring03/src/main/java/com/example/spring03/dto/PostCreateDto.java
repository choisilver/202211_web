package com.example.spring03.dto;

import com.example.spring03.domain.Post;

import lombok.Data;


@Data // 생성자, getter, setter, Tostring, equals..
public class PostCreateDto {
    // DTO 클래스의 필드 이름 = 요청 파라미터 이름.
    private String title;
    private String content;
    private String author;
    
    // DTO 객체를 엔터티 객체로 변환 리턴
    // PostService에서 PostRepository를 호출 할 때
    public Post toEntity() {
        return Post.builder().title(title).content(content).author(author).build();
    }
}
