package com.michael.blog_mvc.service;

import com.michael.blog_mvc.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
}
