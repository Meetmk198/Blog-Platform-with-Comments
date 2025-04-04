package com.meet.blog_post.tags.dto;

import jakarta.validation.constraints.NotNull;

public class TagDto {

    private Long tagId;
    @NotNull
    private String tagName;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
