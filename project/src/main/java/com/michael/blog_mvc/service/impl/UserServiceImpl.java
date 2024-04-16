package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.entity.Role;
import com.michael.blog_mvc.entity.User;
import com.michael.blog_mvc.payload.request.RegistrationRequest;
import com.michael.blog_mvc.payload.response.UserResponse;
import com.michael.blog_mvc.repository.RoleRepository;
import com.michael.blog_mvc.repository.UserRepository;
import com.michael.blog_mvc.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse saveUser(RegistrationRequest request) {

        Role role = roleRepository.findByName("ROLE_GUEST")
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        User user = User.builder()
                .name(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .roles(Arrays.asList(role))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
