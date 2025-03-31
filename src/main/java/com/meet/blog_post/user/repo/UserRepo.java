package com.meet.blog_post.user.repo;

import com.meet.blog_post.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    Optional<User> findByEmail(String email);
}
