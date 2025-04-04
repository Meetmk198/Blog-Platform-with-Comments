package com.meet.blog_post.comments.controller;

import com.meet.blog_post.comments.dto.CommentDto;
import com.meet.blog_post.comments.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseEntity saveComment(@RequestBody @Valid CommentDto commentDto){
        return commentService.saveComment(commentDto);
    }

    @GetMapping("/comments/{blogpostId}")
    public ResponseEntity saveComment(@PathVariable Long blogpostId){
        return commentService.getAllCommentsForBlogpost(blogpostId);
    }
}
