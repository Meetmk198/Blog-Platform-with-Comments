package com.meet.blog_post.comments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meet.blog_post.blogpost.models.Blogpost;
import com.meet.blog_post.common.models.AdvancedBaseEntity;
import com.meet.blog_post.user.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "blog_post_comments")
public class Comment extends AdvancedBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @NotNull
    @Column(name = "comment_text", length = 500)
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "blog_post_id",nullable = false)
    private Blogpost blogpost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

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

    public Blogpost getBlogpost() {
        return blogpost;
    }

    public void setBlogpost(Blogpost blogpost) {
        this.blogpost = blogpost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
