package com.meet.blog_post.user.controller;

import com.meet.blog_post.auth.security.JwtServices;
import com.meet.blog_post.exception.ApplicationException;
import com.meet.blog_post.response.SuccessResponse;
import com.meet.blog_post.user.dto.LoginDto;
import com.meet.blog_post.user.models.User;
import com.meet.blog_post.user.service.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/public")
public class UserController {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtServices jwtServices;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signUp")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid User user){
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return myUserDetailsService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> loginUser(@RequestBody @Valid LoginDto loginDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new ApplicationException(e.getMessage(),HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  jwtServices.issueToken(loginDto.getUsername());
    }
}
