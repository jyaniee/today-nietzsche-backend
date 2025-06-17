package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.dto.PostDetailResponse;
import com.jyanie.nietzsche.dto.PostRequest;
import com.jyanie.nietzsche.dto.PostListResponse;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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


    public void updatePost(Long id, PostRequest request, User user){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if(!post.getAuthor().getId().equals(user.getId())){
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());

        postRepository.save(post);
    }

    public void deletePost(Long id, User user){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if(!post.getAuthor().getId().equals(user.getId())){
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
