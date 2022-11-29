package com.example.spring03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "POSTS")  
//-> 엔터티 클래스와 데이터베이스 테이블의 이름이 다르면 반드시 name 속성을 지정.(테이블 이름과 클래스 이름이 다를 경우) 
@SequenceGenerator(name = "POSTS_SEQ_GEN", sequenceName = "POSTS_SEQ" , initialValue = 1, allocationSize = 1)
//-> Oracle의 시퀀스 객체를 고유키 생성에 사용하기 위해서.  name은 만들면되고, sequenceName은 DB에 있는것과 동일해야함.

public class Post extends BaseTimeEntity {
    // 엔터티 클래스에서 @MappedSuperclass로 설정된 클래스를 상속하면
    // 상위 클래스의 필드들도 테이블의 컬럼으로 작성됨.
    
    @Id  // Primary Key(고유키) (고유키가 없으면 엔터티 클래스가 제대로 되지 않음)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN") // POSTS 테이블, id 컬럼은 sequence, primary key 직관적으로 테이블을 알수 있음.
    private Integer id;
   
    @Column(nullable = false) // Not Null 제약 조건 
    private String title;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private String author;
    
    // setter를 만들지 않고 따로 메서드를 만들어서 update함.
    public Post update(String title, String content) {
        this.title= title;
        this.content = content;
        return this;
    }
    
    

}
