package com.meet.blog_post.blogpost.service;

import com.meet.blog_post.blogpost.dto.BlogPostMst;
import com.meet.blog_post.blogpost.models.Blogpost;
import com.meet.blog_post.blogpost.repo.BlogPostRepo;
import com.meet.blog_post.category.dto.CategoryDto;
import com.meet.blog_post.category.model.Category;
import com.meet.blog_post.category.repo.CategoryRepo;
import com.meet.blog_post.comments.dto.CommentDto;
import com.meet.blog_post.comments.repo.CommentRepo;
import com.meet.blog_post.exception.ApplicationException;
import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.tags.dto.TagDto;
import com.meet.blog_post.tags.model.Tag;
import com.meet.blog_post.tags.repo.TagRepo;
import com.meet.blog_post.user.dto.UserDTO;
import com.meet.blog_post.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meet.blog_post.common.constants.CmnConstants.getLoggedInUser;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    BlogPostRepo blogPostRepo;
    @Autowired
    TagRepo tagRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CommentRepo commentRepo;

    @Override
    public ResponseEntity<SuccessResponse> savePost(BlogPostMst blogPostMst) {
        User loggedInUser = getLoggedInUser();
        Blogpost blogpost = new Blogpost();
        blogpost.setContent(blogPostMst.getContent());
        blogpost.setTitle(blogPostMst.getTitle());
        blogpost.setUser(loggedInUser);
        //to map Tags associated with this blog-post
        blogpost.setTags(blogPostMst.getTags().stream().map(tag -> {
            return tagRepo.findById(tag.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tag.getTagId(), HttpStatus.NOT_FOUND));
        }).collect(Collectors.toList()));
        //to map Categories associated with this blog-post
        blogpost.setCategories(blogPostMst.getCategories().stream().map(category -> {
            return categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + category.getCategoryId(), HttpStatus.NOT_FOUND));
        }).collect(Collectors.toList()));
        blogPostRepo.save(blogpost);
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.CREATED, "Blog Posted SuccessFully.");
        return ResponseEntity.status(successResponse.getStatus()).body(successResponse);
    }

    @Override
    public ResponseEntity getAllBlogsForUser(Long id) {

        Optional<List<Blogpost>> lstBlogs = blogPostRepo.findByUserId(id);
        if (lstBlogs.isPresent() && !lstBlogs.get().isEmpty()) {
            List<BlogPostMst> lstBlogPostMst = lstBlogs.get().stream().map(blogpost -> {
                BlogPostMst blogPostMst = new BlogPostMst();
                blogPostMst.setUserId(blogpost.getUser().getId());
                blogPostMst.setBlogPostId(blogpost.getBlogPostId());
                blogPostMst.setTitle(blogpost.getTitle());
                blogPostMst.setContent(blogpost.getContent());

                //to map Tags associated with this blog-post
                blogPostMst.setTags(blogpost.getTags().stream().map(tag -> {
                    Tag childTag = tagRepo.findById(tag.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tag.getTagId(), HttpStatus.NOT_FOUND));
                    TagDto tagDto = new TagDto();
                    tagDto.setTagId(childTag.getTagId());
                    tagDto.setTagName(childTag.getTagName());
                    return tagDto;
                }).collect(Collectors.toList()));

                //to map Categories associated with this blog-post
                blogPostMst.setCategories(blogpost.getCategories().stream().map(category -> {
                    Category childCategory = categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + category.getCategoryId(), HttpStatus.NOT_FOUND));
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setCategoryId(childCategory.getCategoryId());
                    categoryDto.setCategoryName(childCategory.getCategoryName());
                    return categoryDto;
                }).collect(Collectors.toList()));

                //to map Comments associated with this blog-post
                blogPostMst.setComments(commentRepo.findAllByBlogpostBlogPostId(blogpost.getBlogPostId()).get().stream().map(comment ->
                        {
                            CommentDto commentDto = new CommentDto();
                            commentDto.setCommentId(comment.getCommentId());
                            commentDto.setCommentText(comment.getCommentText());
                            commentDto.setBlogpostId(comment.getBlogpost().getBlogPostId());
                            commentDto.setParentCommentId(comment.getParentCommentId());
                            UserDTO comUser = new UserDTO();
                            comUser.setUsername(comment.getUser().getUsername());
                            commentDto.setUserDTO(comUser);
                            return commentDto;
                        }
                ).collect(Collectors.toList()));
                return blogPostMst;
            }).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "success", lstBlogPostMst));
        }
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
                //to map Tags associated with this blog-post
                blogpost.setTags(blogPostMst.getTags().stream().map(tag -> {
                    return tagRepo.findById(tag.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tag.getTagId(), HttpStatus.NOT_FOUND));
                }).collect(Collectors.toList()));
                //to map Categories associated with this blog-post
                blogpost.setCategories(blogPostMst.getCategories().stream().map(category -> {
                    return categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + category.getCategoryId(), HttpStatus.NOT_FOUND));
                }).collect(Collectors.toList()));
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
                for (Tag tag : tempBlogPost.get().getTags()) {

                }

                for (Tag tag : tempBlogPost.get().getTags()) {

                }
                blogPostRepo.delete(blogpost);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "Blog Deleted SuccessFully."));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SuccessResponse(HttpStatus.UNAUTHORIZED, "You can not delete this blog."));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "no Blog found For id " + blogPostId));
    }

    @Override
    public ResponseEntity getAllBlogs() {
       List<Blogpost> lstBlogs = blogPostRepo.findAll();
        if (lstBlogs != null && !lstBlogs.isEmpty()) {
            List<BlogPostMst> lstBlogPostMst = lstBlogs.stream().map(blogpost -> {
                BlogPostMst blogPostMst = new BlogPostMst();
                blogPostMst.setUserId(blogpost.getUser().getId());
                blogPostMst.setBlogPostId(blogpost.getBlogPostId());
                blogPostMst.setTitle(blogpost.getTitle());
                blogPostMst.setContent(blogpost.getContent());

                //to map Tags associated with this blog-post
                blogPostMst.setTags(blogpost.getTags().stream().map(tag -> {
                    Tag childTag = tagRepo.findById(tag.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tag.getTagId(), HttpStatus.NOT_FOUND));
                    TagDto tagDto = new TagDto();
                    tagDto.setTagId(childTag.getTagId());
                    tagDto.setTagName(childTag.getTagName());
                    return tagDto;
                }).collect(Collectors.toList()));

                //to map Categories associated with this blog-post
                blogPostMst.setCategories(blogpost.getCategories().stream().map(category -> {
                    Category childCategory = categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + category.getCategoryId(), HttpStatus.NOT_FOUND));
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setCategoryId(childCategory.getCategoryId());
                    categoryDto.setCategoryName(childCategory.getCategoryName());
                    return categoryDto;
                }).collect(Collectors.toList()));

                //to map Comments associated with this blog-post
                blogPostMst.setComments(commentRepo.findAllByBlogpostBlogPostId(blogpost.getBlogPostId()).get().stream().map(comment ->
                        {
                            CommentDto commentDto = new CommentDto();
                            commentDto.setCommentId(comment.getCommentId());
                            commentDto.setCommentText(comment.getCommentText());
                            commentDto.setBlogpostId(comment.getBlogpost().getBlogPostId());
                            commentDto.setParentCommentId(comment.getParentCommentId());
                            UserDTO comUser = new UserDTO();
                            comUser.setUsername(comment.getUser().getUsername());
                            commentDto.setUserDTO(comUser);
                            return commentDto;
                        }
                ).collect(Collectors.toList()));
                return blogPostMst;
            }).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "success", lstBlogPostMst));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "Data found null"));
    }

    @Override
    public ResponseEntity getAllBlogsByCategories(String categoryName) {
        Category category = categoryRepo.findByCategoryNameIgnoreCase(categoryName);
        List<Blogpost> lstBlogs = blogPostRepo.findAllByCategoriesCategoryId(category.getCategoryId());
        if (lstBlogs != null && !lstBlogs.isEmpty()) {
            List<BlogPostMst> lstBlogPostMst = lstBlogs.stream().map(blogpost -> {
                BlogPostMst blogPostMst = new BlogPostMst();
                blogPostMst.setUserId(blogpost.getUser().getId());
                blogPostMst.setBlogPostId(blogpost.getBlogPostId());
                blogPostMst.setTitle(blogpost.getTitle());
                blogPostMst.setContent(blogpost.getContent());

                //to map Tags associated with this blog-post
                blogPostMst.setTags(blogpost.getTags().stream().map(tags -> {
                    Tag childTag = tagRepo.findById(tags.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tags.getTagId(), HttpStatus.NOT_FOUND));
                    TagDto tagDto = new TagDto();
                    tagDto.setTagId(childTag.getTagId());
                    tagDto.setTagName(childTag.getTagName());
                    return tagDto;
                }).collect(Collectors.toList()));

                //to map Categories associated with this blog-post
                blogPostMst.setCategories(blogpost.getCategories().stream().map(categories -> {
                    Category childCategory = categoryRepo.findById(categories.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + categories.getCategoryId(), HttpStatus.NOT_FOUND));
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setCategoryId(childCategory.getCategoryId());
                    categoryDto.setCategoryName(childCategory.getCategoryName());
                    return categoryDto;
                }).collect(Collectors.toList()));

                //to map Comments associated with this blog-post
                blogPostMst.setComments(commentRepo.findAllByBlogpostBlogPostId(blogpost.getBlogPostId()).get().stream().map(comment ->
                        {
                            CommentDto commentDto = new CommentDto();
                            commentDto.setCommentId(comment.getCommentId());
                            commentDto.setCommentText(comment.getCommentText());
                            commentDto.setBlogpostId(comment.getBlogpost().getBlogPostId());
                            commentDto.setParentCommentId(comment.getParentCommentId());
                            UserDTO comUser = new UserDTO();
                            comUser.setUsername(comment.getUser().getUsername());
                            commentDto.setUserDTO(comUser);
                            return commentDto;
                        }
                ).collect(Collectors.toList()));
                return blogPostMst;
            }).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "success", lstBlogPostMst));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "Data found null"));
    }

    @Override
    public ResponseEntity getAllBlogsByTags(String tagName) {
        Tag tag = tagRepo.findByTagNameIgnoreCase(tagName);
        List<Blogpost> lstBlogs = blogPostRepo.findAllByTagsTagId(tag.getTagId());
        if (lstBlogs != null && !lstBlogs.isEmpty()) {
            List<BlogPostMst> lstBlogPostMst = lstBlogs.stream().map(blogpost -> {
                BlogPostMst blogPostMst = new BlogPostMst();
                blogPostMst.setUserId(blogpost.getUser().getId());
                blogPostMst.setBlogPostId(blogpost.getBlogPostId());
                blogPostMst.setTitle(blogpost.getTitle());
                blogPostMst.setContent(blogpost.getContent());

                //to map Tags associated with this blog-post
                blogPostMst.setTags(blogpost.getTags().stream().map(tags -> {
                    Tag childTag = tagRepo.findById(tags.getTagId()).orElseThrow(() -> new ApplicationException("Tag does not exist with id-> " + tags.getTagId(), HttpStatus.NOT_FOUND));
                    TagDto tagDto = new TagDto();
                    tagDto.setTagId(childTag.getTagId());
                    tagDto.setTagName(childTag.getTagName());
                    return tagDto;
                }).collect(Collectors.toList()));

                //to map Categories associated with this blog-post
                blogPostMst.setCategories(blogpost.getCategories().stream().map(category -> {
                    Category childCategory = categoryRepo.findById(category.getCategoryId()).orElseThrow(() -> new ApplicationException("Category does not exist with id-> " + category.getCategoryId(), HttpStatus.NOT_FOUND));
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setCategoryId(childCategory.getCategoryId());
                    categoryDto.setCategoryName(childCategory.getCategoryName());
                    return categoryDto;
                }).collect(Collectors.toList()));

                //to map Comments associated with this blog-post
                blogPostMst.setComments(commentRepo.findAllByBlogpostBlogPostId(blogpost.getBlogPostId()).get().stream().map(comment ->
                        {
                            CommentDto commentDto = new CommentDto();
                            commentDto.setCommentId(comment.getCommentId());
                            commentDto.setCommentText(comment.getCommentText());
                            commentDto.setBlogpostId(comment.getBlogpost().getBlogPostId());
                            commentDto.setParentCommentId(comment.getParentCommentId());
                            UserDTO comUser = new UserDTO();
                            comUser.setUsername(comment.getUser().getUsername());
                            commentDto.setUserDTO(comUser);
                            return commentDto;
                        }
                ).collect(Collectors.toList()));
                return blogPostMst;
            }).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK, "success", lstBlogPostMst));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(HttpStatus.NOT_FOUND, "Data found null"));
    }
}
