package com.jyanie.nietzsche.controller;

import com.jyanie.nietzsche.dto.PostRequest;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Post saved = postService.createPost(request, user);
        return ResponseEntity.ok(saved);
    }
}
