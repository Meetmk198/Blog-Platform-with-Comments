package com.meet.blog_post.blogpost.controller;

import com.meet.blog_post.blogpost.dto.BlogPostMst;
import com.meet.blog_post.blogpost.service.BlogPostService;
import com.meet.blog_post.user.models.User;
import com.meet.blog_post.user.service.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.meet.blog_post.Utils.UserUtils.getUserId;

@RestController
@RequestMapping("/blog")
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @PostMapping("/")
    public ResponseEntity save(@RequestBody @Valid BlogPostMst blogPostMst){
        String username = getUserId();
        Optional<User> userDetails  = myUserDetailsService.findByUsername(username);
        if (userDetails.isPresent())
            blogPostMst.setUserId(userDetails.get().getId());
        return blogPostService.savePost(blogPostMst);
    }

    @GetMapping("/")
    public ResponseEntity getAllBlogsForUser(){
        String username = getUserId();
        Optional<User> userDetails  = myUserDetailsService.findByUsername(username);
        return blogPostService.getAllBlogsForUser(userDetails.get().getId());
    }

    @GetMapping("/all")
    public ResponseEntity getAllBlogs(){
        return blogPostService.getAllBlogs();
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity getAllBlogsByTags(@PathVariable String tag){
        return blogPostService.getAllBlogsByTags(tag);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity getAllBlogsByCategories(@PathVariable String category){
        return blogPostService.getAllBlogsByCategories(category);
    }

    @PutMapping("/{blogPostId}")
    public ResponseEntity updateBlog(@PathVariable Long blogPostId,@RequestBody @Valid BlogPostMst blogPostMst){
        String username = getUserId();
        Optional<User> userDetails  = myUserDetailsService.findByUsername(username);

        return blogPostService.updateBlogById(userDetails.get().getId(),blogPostMst,blogPostId);
    }

    @DeleteMapping("/{blogPostId}")
    public ResponseEntity deleteBlog(@PathVariable Long blogPostId){
        String username = getUserId();
        Optional<User> userDetails  = myUserDetailsService.findByUsername(username);
        return blogPostService.deleteBlogById(userDetails.get().getId(),blogPostId);
    }
}
