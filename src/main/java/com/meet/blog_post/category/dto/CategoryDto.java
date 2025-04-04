package com.meet.blog_post.category.dto;

import jakarta.validation.constraints.NotNull;

public class CategoryDto {

    private Long categoryId;
    @NotNull
    private String categoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
