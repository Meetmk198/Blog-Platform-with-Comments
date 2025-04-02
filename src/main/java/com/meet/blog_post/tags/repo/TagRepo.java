package com.meet.blog_post.tags.repo;

import com.meet.blog_post.tags.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface TagRepo extends JpaRepository<Tag,Long> {
}
