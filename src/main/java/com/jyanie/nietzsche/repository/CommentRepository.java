package com.jyanie.nietzsche.repository;

import com.jyanie.nietzsche.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    int countByPostId(Long postId); // postId로 댓글 수 조회
}
