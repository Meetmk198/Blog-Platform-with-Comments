package com.meet.blog_post.category.service;

import com.meet.blog_post.category.dto.CategoryDto;
import com.meet.blog_post.category.model.Category;
import com.meet.blog_post.category.repo.CategoryRepo;
import com.meet.blog_post.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo CategoryRepo;
    @Override
    public ResponseEntity saveCategory(CategoryDto CategoryDto) {
        Category Category = new Category();
        Category.setCategoryName(CategoryDto.getCategoryName());
        CategoryRepo.save(Category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(HttpStatus.CREATED,"Category Created Successfully."));
    }

    @Override
    public ResponseEntity findAll() {

        List<Category> lstCategorys = CategoryRepo.findAll();
        if (!lstCategorys.isEmpty()){
        List<CategoryDto> lstCategoryDTOs = lstCategorys.stream().map(Category -> {
            CategoryDto CategoryDto = new CategoryDto();
            CategoryDto.setCategoryName(Category.getCategoryName());
            CategoryDto.setCategoryId(Category.getCategoryId());
            return CategoryDto;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK,lstCategoryDTOs));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(HttpStatus.OK,"No Category Found."));
    }
}
