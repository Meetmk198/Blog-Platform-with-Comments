package com.meet.blog_post.category.controller;

import com.meet.blog_post.category.dto.CategoryDto;
import com.meet.blog_post.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService CategoryService;

    @PostMapping
    public ResponseEntity saveCategory(@RequestBody @Valid CategoryDto CategoryDto) {
        return CategoryService.saveCategory(CategoryDto);
    }
    @GetMapping
    public ResponseEntity getAllCategorys() {
        return CategoryService.findAll();
    }

}
