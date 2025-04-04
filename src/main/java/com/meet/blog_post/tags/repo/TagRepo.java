package com.meet.blog_post.tags.repo;

import com.meet.blog_post.tags.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag,Long> {
    Tag findByTagNameIgnoreCase(String tag);
}
