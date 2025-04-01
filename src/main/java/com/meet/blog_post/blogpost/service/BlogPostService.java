package com.meet.blog_post.blogpost.service;

import com.meet.blog_post.blogpost.dto.BlogPostMst;
import com.meet.blog_post.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface BlogPostService {
    ResponseEntity<SuccessResponse> savePost(BlogPostMst blogPostMst);

    ResponseEntity getAllBlogsForUser(Long id);

    ResponseEntity updateBlogById(Long userId, BlogPostMst blogPostMst, Long blogPostId);

    ResponseEntity deleteBlogById(Long userId, Long blogPostId);
}
