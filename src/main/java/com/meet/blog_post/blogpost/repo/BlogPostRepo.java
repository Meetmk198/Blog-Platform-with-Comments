package com.meet.blog_post.blogpost.repo;

import com.meet.blog_post.blogpost.models.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepo extends JpaRepository<Blogpost,Long> {
    Optional<List<Blogpost>> findByUserId(Long id);
}
