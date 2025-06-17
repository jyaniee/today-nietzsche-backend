package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.dto.PostDetailResponse;
import com.jyanie.nietzsche.dto.PostRequest;
import com.jyanie.nietzsche.dto.PostListResponse;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.CommentRepository;
import com.jyanie.nietzsche.repository.PostLikeRepository;
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
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

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
                .map(post -> {
                    int likeCount = postLikeRepository.countByPostId(post.getId()); // 좋아요 수 가져오기
                    int count = commentRepository.countByPostId(post.getId()); // 댓글 수 가져오기
                    return PostListResponse.from(post, count, likeCount);
                })
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

    public Post getAndIncreaseViews(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        post.setViews(post.getViews() + 1);
        return post;
    }
}
