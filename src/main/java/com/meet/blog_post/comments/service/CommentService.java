package com.meet.blog_post.comments.service;

import com.meet.blog_post.comments.dto.CommentDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity saveComment(CommentDto commentDto);
    ResponseEntity getAllCommentsForBlogpost(Long blogpostId);
}
