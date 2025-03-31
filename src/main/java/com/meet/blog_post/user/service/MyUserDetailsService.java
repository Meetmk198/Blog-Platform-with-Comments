package com.meet.blog_post.user.service;

import com.meet.blog_post.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {
    ResponseEntity<Object> registerUser(User user);

    UserDetails findByUsername(String email);
}
