package com.michael.blog_mvc.service;

import com.michael.blog_mvc.payload.request.CommentRequest;
import com.michael.blog_mvc.payload.response.CommentResponse;
import com.michael.blog_mvc.payload.response.MessageResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(String postUrl, CommentRequest commentRequest);

    List<CommentResponse> findAllComments();

    MessageResponse deleteComment(Long commentId);

    List<CommentResponse> findCommentByPost();
}
