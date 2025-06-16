package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.dto.PostDetailResponse;
import com.jyanie.nietzsche.dto.PostRequest;
import com.jyanie.nietzsche.dto.PostListResponse;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostRequest request, User user){
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .author(user)
                .views(0)
                .likes(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    public List<PostListResponse> getAllPosts(){
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        return posts.stream()
                .map(PostListResponse::from)
                .toList();
    }

    public PostDetailResponse getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return PostDetailResponse.from(post);
    }
}
