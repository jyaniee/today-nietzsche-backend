package com.jyanie.nietzsche.dto;

import com.jyanie.nietzsche.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getAuthor().getName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
