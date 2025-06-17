package com.jyanie.nietzsche.repository;

import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.PostLike;
import com.jyanie.nietzsche.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post); // 좋아요 중복 확인
    int countByPost(Post post); // 좋아요 갯수
    int countByPostId(Long postId);
    void deleteByUserAndPost(User user, Post post); // 좋아요 취소
}
