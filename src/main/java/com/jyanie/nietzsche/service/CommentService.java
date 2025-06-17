package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.dto.CommentResponse;
import com.jyanie.nietzsche.entity.Comment;
import com.jyanie.nietzsche.entity.Post;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.CommentRepository;
import com.jyanie.nietzsche.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment addComment(Long postId, String content, User user){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(content)
                .author(user)
                .post(post)
                .build();

        return commentRepository.save(comment);
    }

    public List<CommentResponse> getComments(Long postId){
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public void deleteComment(Long commentId, User user){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if(!comment.getAuthor().getId().equals(user.getId())){
            throw new RuntimeException("본인의 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
