package com.michael.blog_mvc.controllers;

import com.michael.blog_mvc.entity.User;
import com.michael.blog_mvc.payload.request.RegistrationRequest;
import com.michael.blog_mvc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        RegistrationRequest user = new RegistrationRequest();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") @Valid RegistrationRequest user,
                           BindingResult result,
                           Model model) {
        User existingUser = userService.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            result.rejectValue("email", null, String.format("User with email: %s already exists", user.getEmail()));
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
