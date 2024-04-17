package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.entity.ConfirmationToken;
import com.michael.blog_mvc.repository.ConfirmationTokenRepository;
import com.michael.blog_mvc.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl  implements ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }
}
