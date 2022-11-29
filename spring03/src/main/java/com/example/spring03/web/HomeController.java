package com.example.spring03.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring03.domain.Post;
import com.example.spring03.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller  // 스프링 컨트롤러 컴포넌트
@RequiredArgsConstructor
public class HomeController {
    
    private final PostService postService;  // 생성자에 의해서 의존성 주입.
    
    @GetMapping("/") // 요청 URL/방식 매핑.
    public String home(Model model) {
        log.info("home()");
        
        List<Post> list = postService.read(); // DB에서 포스트 목록 전체 검색.
        model.addAttribute("list", list); // 뷰에 전달하는 모델 데이터.
        
        return "/post/list";
        // view 이름 -> src/main/resources/templates/파일이름.html
    }

    
}
