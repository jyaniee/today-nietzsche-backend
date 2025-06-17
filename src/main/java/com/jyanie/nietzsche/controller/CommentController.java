package com.jyanie.nietzsche.controller;

import com.jyanie.nietzsche.dto.CommentRequest;
import com.jyanie.nietzsche.dto.CommentResponse;
import com.jyanie.nietzsche.entity.Comment;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.security.CustomUserDetails;
import com.jyanie.nietzsche.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> create(@PathVariable Long postId,
                                    @RequestBody CommentRequest request,
                                    Authentication auth) {

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자만 댓글을 작성할 수 있습니다.");
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        Comment saved = commentService.addComment(postId, request.getContent(), user);
        return ResponseEntity.ok(CommentResponse.from(saved));
    }

    // 댓글 목록 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getAll(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("인증된 사용자만 삭제할 수 있습니다.");
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }
}
