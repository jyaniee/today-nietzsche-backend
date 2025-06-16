package com.jyanie.nietzsche.controller;

import com.jyanie.nietzsche.dto.PostDetailResponse;
import com.jyanie.nietzsche.dto.PostRequest;
import com.jyanie.nietzsche.dto.PostListResponse;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.security.CustomUserDetails;
import com.jyanie.nietzsche.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        //User user = (User) authentication.getPrincipal();
        Post saved = postService.createPost(request, user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser(); // 사용자 정보는 현재 안 쓰지만, 인증된 요청만 허용하기 위해 받아둠

        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


}
