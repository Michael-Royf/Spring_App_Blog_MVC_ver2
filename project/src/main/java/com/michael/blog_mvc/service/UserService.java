package com.michael.blog_mvc.service;

import com.michael.blog_mvc.entity.User;
import com.michael.blog_mvc.payload.request.RegistrationRequest;
import com.michael.blog_mvc.payload.response.UserResponse;

import java.util.Optional;

public interface UserService {
    UserResponse saveUser(RegistrationRequest request);

    Optional<User> findByEmail(String email);
}

