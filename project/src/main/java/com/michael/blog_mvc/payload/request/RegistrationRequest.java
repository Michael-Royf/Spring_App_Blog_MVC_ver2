package com.michael.blog_mvc.payload.request;

import com.michael.blog_mvc.validation.PasswordMatches;
import com.michael.blog_mvc.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@PasswordMatches
public class RegistrationRequest {

    @NotEmpty(message = "Username should not be empty or null")
    private String username;

    @NotEmpty(message = "First name should not be empty or null")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty or null")
    private String lastName;

    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty or null")
    @ValidPassword
    private String password;

    @NotEmpty(message = "Password should not be empty")
    @ValidPassword

    private String matchingPassword;
}

//Email is invalid or already taken
