package com.jyanie.nietzsche.dto;


import com.jyanie.nietzsche.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class PostListResponse {
    private Long id;
    private String title;
    // private String content;
    private String category;
    private String authorName;
    private int views;
    private int likes;
    private int commentCount;
    private LocalDateTime createdAt;

    public static PostListResponse from(Post post, int commentCount) {
        return PostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .authorName(post.getAuthor().getName())
                .views(post.getViews())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .commentCount(commentCount)
                .build();
    }
}
