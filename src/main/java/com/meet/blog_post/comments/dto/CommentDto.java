package com.meet.blog_post.comments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meet.blog_post.blogpost.models.Blogpost;
import com.meet.blog_post.user.dto.UserDTO;
import com.meet.blog_post.user.models.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class CommentDto {

    private Long commentId;
    @NotNull
    @Column(name = "comment_text", length = 500)
    private String commentText;

    private Long blogpostId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO userDTO;

    private Long parentCommentId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getBlogpostId() {
        return blogpostId;
    }

    public void setBlogpostId(Long blogpostId) {
        this.blogpostId = blogpostId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
