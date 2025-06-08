package com.jyanie.nietzsche.repository;

import com.jyanie.nietzsche.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
