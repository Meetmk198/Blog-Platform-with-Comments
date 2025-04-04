package com.meet.blog_post.category.repo;

import com.meet.blog_post.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryNameIgnoreCase(String categoryName);
}
