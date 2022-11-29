package com.example.spring03.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring03.dto.ReplyReadDto;
import com.example.spring03.dto.ReplyRegisterDto;
import com.example.spring03.dto.ReplyUpdateDto;
import com.example.spring03.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController //REST 서비스에서 사용되는 컨트롤러 객체
// -> @Controller 클래스의 메서드 리턴값(String) => 뷰 이름.(파일이름)
// -> @RestController 클래스의 메서트 리턴값 => 클라이언트로 전송되는 값! json문자열
@RequestMapping("/api/reply")
public class ReplyRestController {
    
    private final ReplyService replyService;
    
    @PostMapping // 댓글 등록 REST 서비스
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        // Ajax 요청에서 서버로 보내는 데이터 HTTP 패킷의 body에 포함됨.
        // @RequestBoody: HTTP 패킷 body에 포함된 데이터(파라미터)를 분석할 때 사용.
        
        log.info("registerReply()");
        // 댓글 데이터베이스 테이블에 등록.
        Integer replyId = replyService.create(dto);
        
        //  작성된 댓글의 아이디를 응답 데이터(response data)로 만들어서
        // Ajax 요청에 대한 성공 응답을 전송.
        return ResponseEntity.ok(replyId); 
    }
    
    @GetMapping("/all/{postId}") // path 변수
    // 직렬화? ResponseEntity는 직렬화가 가능한 타입을 사용해야 함;; 
    // REST controller에서 ResponseEntity를 리턴할 때, 데이터 타입으로 엔터티 클래스를 사용하면 안됨.
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(@PathVariable Integer postId){
        // @PathVariable: 요청 주소에서 변수처럼 값이 변하는 부분.
        log.info("readAllReplies(postId={})", postId);
        
        List<ReplyReadDto> list = replyService.readReplies(postId);
        log.info("# of list = {}", list.size());
        
        return ResponseEntity.ok(list);
        // Java의 List 타입 객체가 JSON(JavaScript Object Notation) 문자열로 변환 되서
        // 클라이언트(브라우저)로 전송.
    }
    
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyReadDto> getRely(@PathVariable Integer replyId){
        log.info("getReply(replyid = {})" , replyId);
        
        ReplyReadDto dto = replyService.readReply(replyId);
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyId){
        log.info("deleteReply(replyId={}", replyId);
        
        Integer result = replyService.delete(replyId);
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/{replyId}")
    public ResponseEntity<Integer> updateReply(@PathVariable Integer replyId, @RequestBody ReplyUpdateDto dto){
        log.info("updateReply(replyId={}, dto={})",replyId, dto);
        
        dto.setReplyId(replyId); // dto에 댓글 아이디를 저장.
        Integer result = replyService.update(dto);
        
        return ResponseEntity.ok(result);
    }


    
    
    
}
