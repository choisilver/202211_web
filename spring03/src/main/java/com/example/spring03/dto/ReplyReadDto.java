package com.example.spring03.dto;

import java.time.LocalDateTime;

import com.example.spring03.domain.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@ToString
@Builder
public class ReplyReadDto {
    // 직렬화가 가능한(Serializable을 구현한 타입) 필드로만 구성. Reply타입과 완전 동일함.
    
    private Integer replyId; // 댓글 번호
    private Integer postId; // 댓글이 달린 포스트 번호
    private String replyText; // 댓글 내용
    private String writer; // 댓글 작성자
    private LocalDateTime createdTime; // 댓글 최초 작성 시간
    private LocalDateTime modifiedTime; // 댓글 최종 수정 시간
    
    // Entity 객체에서 DTO 객체를 생성해서 리턴하는 메서드
    public static ReplyReadDto fromEntity(Reply entity) {
        return ReplyReadDto.builder()
                .replyId(entity.getId())
                .postId(entity.getPost().getId())
                .replyText(entity.getReplyText())
                .writer(entity.getWriter())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifiedTime())
                .build();
    
    }
    
}
