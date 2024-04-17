package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.entity.ConfirmationToken;
import com.michael.blog_mvc.entity.User;
import com.michael.blog_mvc.entity.enumeration.UserRole;
import com.michael.blog_mvc.payload.request.RegistrationRequest;
import com.michael.blog_mvc.repository.ConfirmationTokenRepository;
import com.michael.blog_mvc.repository.UserRepository;
import com.michael.blog_mvc.service.ConfirmationTokenService;
import com.michael.blog_mvc.service.EmailSender;
import com.michael.blog_mvc.service.UserService;
import com.michael.blog_mvc.util.EmailBuilder;
import com.michael.blog_mvc.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.michael.blog_mvc.constant.SecurityConstant.VERIFICATION_TOKEN_EXPIRED;
import static com.michael.blog_mvc.constant.SecurityConstant.VERIFICATION_TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenService tokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailBuilder emailBuilder;
    private final EmailSender emailSender;
    private final RandomUtils randomUtils;

    public static final String LINK_FOR_CONFIRMATION = "http://localhost:8080/register/confirm?token=";

    public static final String EMAIL_ALREADY_CONFIRMED = "Email already confirmed";
    public static final String CONFIRMED = "CONFIRMED";

    @Override
    public String saveUser(RegistrationRequest request) {

        //check email and username

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(UserRole.ROLE_USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(false)
                .isNotLocked(true)
                .build();

        user = userRepository.save(user);

        String token = randomUtils.generateTokenForVerification();
        ConfirmationToken confirmationToken =
                ConfirmationToken.builder()
                        .token(token)
                        .createdAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(15))
                        .user(user)
                        .build();
        tokenService.saveConfirmationToken(confirmationToken);

        String link = LINK_FOR_CONFIRMATION + token;

        emailSender.sendEmailForVerification(
                user.getEmail(),
                emailBuilder.buildEmailForConfirmationEmail(user.getFirstName(), link));

        return "User registered successfully! \n" +
                "Check your email address.";
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException(VERIFICATION_TOKEN_NOT_FOUND));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new RuntimeException(EMAIL_ALREADY_CONFIRMED);
        }
        LocalDateTime expiredDate = confirmationToken.getExpiredAt();
        if (expiredDate.isBefore(LocalDateTime.now())) {
            throw new RuntimeException(VERIFICATION_TOKEN_EXPIRED);
        }
        confirmationTokenRepository.updateConfirmedDate(token, LocalDateTime.now());
        userRepository.enableUser(confirmationToken.getUser().getEmail());
        return CONFIRMED;
    }

}
