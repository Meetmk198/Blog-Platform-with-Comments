package com.meet.blog_post.category.service;

import com.meet.blog_post.category.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity saveCategory(CategoryDto CategoryDto);

    ResponseEntity findAll();
}
