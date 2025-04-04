package com.meet.blog_post.blogpost.dto;

import com.meet.blog_post.category.dto.CategoryDto;
import com.meet.blog_post.category.model.Category;
import com.meet.blog_post.comments.dto.CommentDto;
import com.meet.blog_post.tags.dto.TagDto;
import com.meet.blog_post.tags.model.Tag;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class BlogPostMst {

    private Long blogPostId;
    private Long userId;
    @Length(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Length(max = 2000, message = "Title cannot exceed 255 content")
    private String content;

    private List<TagDto> tags;
    private List<CategoryDto> categories;

    private List<CommentDto> comments;

    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
