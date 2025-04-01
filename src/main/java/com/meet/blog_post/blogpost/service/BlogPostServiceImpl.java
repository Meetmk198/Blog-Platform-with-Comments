package com.meet.blog_post.blogpost.service;

import com.meet.blog_post.blogpost.dto.BlogPostMst;
import com.meet.blog_post.blogpost.models.Blogpost;
import com.meet.blog_post.blogpost.repo.BlogPostRepo;
import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    BlogPostRepo blogPostRepo;

    @Override
    public ResponseEntity<SuccessResponse> savePost(BlogPostMst blogPostMst) {
        Blogpost blogpost = new Blogpost();
        blogpost.setContent(blogPostMst.getContent());
        blogpost.setTitle(blogPostMst.getTitle());
        blogpost.setUser(new User(blogPostMst.getUserId()));

        blogPostRepo.save(blogpost);
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.CREATED, "Blog Posted SuccessFully.");
        return ResponseEntity.status(successResponse.getStatus()).body(successResponse);
    }

    @Override
    public ResponseEntity getAllBlogsForUser(Long id) {

        Optional<List<Blogpost>> lstBlogs = blogPostRepo.findByUserId(id);
        if (lstBlogs.isPresent() && !lstBlogs.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "success", lstBlogs.get()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "no Data found For User"));

    }

    @Override
    public ResponseEntity updateBlogById(Long userId, BlogPostMst blogPostMst, Long blogPostId) {
        Optional<Blogpost> tempBlogPost = blogPostRepo.findById(blogPostId);
        if (tempBlogPost.isPresent()) {
            Blogpost blogpost = tempBlogPost.get();
            if (blogpost.getUser().getId() == userId) {
                blogpost.setTitle(blogPostMst.getTitle());
                blogpost.setContent(blogPostMst.getContent());
                blogPostRepo.save(blogpost);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "Blog Updated SuccessFully."));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SuccessResponse(HttpStatus.UNAUTHORIZED, "You can not update this blog."));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "no Blog found For id " + blogPostId));
    }

    @Override
    public ResponseEntity deleteBlogById(Long userId, Long blogPostId) {
        Optional<Blogpost> tempBlogPost = blogPostRepo.findById(blogPostId);
        if (tempBlogPost.isPresent()) {
            Blogpost blogpost = tempBlogPost.get();
            if (blogpost.getUser().getId() == userId) {
                blogPostRepo.delete(blogpost);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "Blog Deleted SuccessFully."));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SuccessResponse(HttpStatus.UNAUTHORIZED, "You can not delete this blog."));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "no Blog found For id " + blogPostId));
    }
}
