package com.meet.blog_post.user.service;

import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.user.models.User;
import com.meet.blog_post.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    @Override
    public ResponseEntity<Object> registerUser(User user) {
        if(findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user with email - "+ user.getEmail() +" already Exists.");
        userRepo.save(user);
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK,"user registered successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @Override
    public UserDetails findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
