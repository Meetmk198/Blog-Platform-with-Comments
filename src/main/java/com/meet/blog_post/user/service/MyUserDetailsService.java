package com.meet.blog_post.user.service;

import com.meet.blog_post.user.dto.LoginDto;
import com.meet.blog_post.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface MyUserDetailsService{
    ResponseEntity<Object> registerUser(User user);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

}
