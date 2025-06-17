package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.PostLike;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.PostLikeRepository;
import com.jyanie.nietzsche.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public boolean toggleLike(Long postId, User user){
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        return postLikeRepository.findByUserAndPost(user, post)
                .map(existing -> {
                    postLikeRepository.delete(existing);
                    return false; // 좋아요 취소
                })
                .orElseGet(() -> {
                    postLikeRepository.save(PostLike.builder()
                            .user(user)
                            .post(post)
                            .build());
                    return true; // 좋아요 추가
                });
    }

    public int getLikeCount(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        return postLikeRepository.countByPost(post);
    }
}
