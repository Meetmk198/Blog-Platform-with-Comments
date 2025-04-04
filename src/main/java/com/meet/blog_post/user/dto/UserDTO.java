package com.meet.blog_post.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.meet.blog_post.common.constants.CmnConstants.REGEX_EMAIL;

public class UserDTO {

    private Long id;
    @NotNull(message = "username is Mandatory")
    private String username;
    @NotNull(message = "password is Mandatory")
    private String password;
    private String aboutMe;
    @NotNull(message = "email is Mandatory")
    @Email
    @Pattern(regexp = REGEX_EMAIL, message = "Email should be in valid format")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
