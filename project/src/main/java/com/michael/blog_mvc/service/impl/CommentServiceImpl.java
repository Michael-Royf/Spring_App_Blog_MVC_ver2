package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.entity.Comment;
import com.michael.blog_mvc.entity.Post;
import com.michael.blog_mvc.exceptions.payload.CommentNotFoundException;
import com.michael.blog_mvc.exceptions.payload.PostNotFoundException;
import com.michael.blog_mvc.payload.request.CommentRequest;
import com.michael.blog_mvc.payload.response.CommentResponse;
import com.michael.blog_mvc.payload.response.MessageResponse;
import com.michael.blog_mvc.repository.CommentRepository;
import com.michael.blog_mvc.repository.PostRepository;
import com.michael.blog_mvc.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    private final PostRepository postRepository;


    @Override
    public CommentResponse createComment(String postUrl, CommentRequest commentRequest) {
        Post post = postRepository.findByUrl(postUrl)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with URL: %s mot found", postUrl)));

        Comment comment = Comment.builder()
                .name(commentRequest.getName())
                .email(commentRequest.getEmail())
                .content(commentRequest.getContent())
                .post(post)
                .build();
        comment = commentRepository.save(comment);
        return mapper.map(comment, CommentResponse.class);
    }

    @Override
    public List<CommentResponse> findAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> mapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(String.format("Comment with id: %s not found", commentId)));
        commentRepository.delete(comment);
        return new MessageResponse("Comment was deleted");
    }

    @Override
    public List<CommentResponse> findCommentByPost() {
        return null;
    }
}
