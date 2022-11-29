package com.example.spring03.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring03.domain.Post;
import com.example.spring03.dto.PostCreateDto;
import com.example.spring03.dto.PostUpdateDto;
import com.example.spring03.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    
    
    private final PostService postService;
    
    @GetMapping("/create")  // Get 방식의 /post/create 요청을 처리하는 메서드.
    public void create() { 
        log.info("create()");
        // 컨트롤러 메서드의 리턴 타입이 void인 경우 뷰의 이름은 요청주소와 같음.
        //    src/main/resources/templates  /post/create.html
    }
    
    @PostMapping("/create") // Post 방식의 /post/create 요청을 처리하는 메서드.
    public String create(PostCreateDto dto, RedirectAttributes attrs) {
        // redirect에서 보내기
        log.info("create( dto ={} )", dto);
        
        //  새포스트 작성
        Post entity = postService.create(dto);
        // 작성된 포스트의 번호(id)를 리다이렉트 되는 페이지로 전달. (세션에 저장되는 거임)
        attrs.addFlashAttribute("createdId", entity.getId());
        
        // PRG ( Post, Redirect, Get)
        return "redirect:/";
    }
    
    @GetMapping({"/detail", "/modify" })   // queryString은 request parameter로 찾으면 됨
    // 컨트롤러 메서드가 2개 이상의 요청 주소를 처리할 때는 mapping에서 요청 주소를 배열로 설정.
    public void detail(Integer id, Model model) {
        log.info("detail id={}", id);
        
        
        // TODO: 요청 파라미터 id를 번호로 갖는 포스트 내용을 검색 -> 뷰에 전달.
        Post post = postService.read(id); 
        
        model.addAttribute("post", post);
        
    }
    
    @PostMapping("/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        log.info("delete(id-{})",id);
        Integer postId = postService.delete(id);
        attrs.addFlashAttribute("deletedPostId", postId);
        
        // 삭제 완료 후 목록페이지로 이동
        return "redirect:/";
    }
    
    @PostMapping("/update")
    public String update(PostUpdateDto dto) {
        log.info("update (dto ={})", dto);
        
        Integer id = postService.update(dto);
        
        // 포스트 수정 성공 후에는 상세페이지로 이동(redirect)
        return "redirect:/post/detail?id="+ dto.getId();
    }
    
    @GetMapping("/search")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={})", type, keyword);
        
        List<Post> list = postService.search(type, keyword);
        
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        
        return "/post/list";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
