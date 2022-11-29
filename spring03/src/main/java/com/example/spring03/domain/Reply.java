package com.example.spring03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = { "post" }) // 필드 post는 toString() 메서드를 작성할 때 제외.
@Entity(name = "REPLIES") // 테이블 replies에 매핑되는 엔터티 클래스
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ" , allocationSize = 1)
public class Reply extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "REPLIES_SEQ_GEN" )  // 값을 생성하는 방법
    private Integer id; // 댓글 번호 - Primary Key
    
    @ManyToOne(fetch = FetchType.LAZY ) // 다대일 관계(relation)
    private Post post; // 댓글이 달린 포스트 전체 내용, 두 테이블이 join됨 - Foreign Key, Reply : Post 다대일 
    
    @Column(nullable = false, length = 1000) // 제약조건 없는게 기본값, Not Null 제약조건, 문자열 길이 
    private String replyText; // 댓글 내용
    
    @Column(nullable = false) // Not Null
    private String writer; // 댓글 작성자
    

    // 댓글 수정에서 사용할 메서드. setter 메서드와 동일함.
    public Reply update(String replyText) {
        this.replyText = replyText;
        return this;
    }
}
