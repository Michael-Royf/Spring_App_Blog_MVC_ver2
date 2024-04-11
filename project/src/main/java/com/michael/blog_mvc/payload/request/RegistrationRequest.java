package com.michael.blog_mvc.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "First name should not be empty or null")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty or null")
    private String lastName;
    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty or null")
    private String password;
}
