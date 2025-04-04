package com.meet.blog_post.comments.repo;

import com.meet.blog_post.comments.dto.CommentDto;
import com.meet.blog_post.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findAllByBlogpostBlogPostId(Long blogPostId);
    Optional<List<Comment>> findByBlogpostBlogPostId(Long blogpostId);
}
