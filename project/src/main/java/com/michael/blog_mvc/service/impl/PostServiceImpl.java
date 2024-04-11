package com.michael.blog_mvc.service.impl;

import com.michael.blog_mvc.entity.Post;
import com.michael.blog_mvc.exceptions.payload.PostNotFoundException;
import com.michael.blog_mvc.payload.request.PostRequest;
import com.michael.blog_mvc.payload.response.MessageResponse;
import com.michael.blog_mvc.payload.response.PostResponse;
import com.michael.blog_mvc.repository.PostRepository;
import com.michael.blog_mvc.service.PostService;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public PostResponse creatPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .shortDescription(postRequest.getShortDescription())
                .content(postRequest.getContent())
                .url(createUrl(postRequest.getTitle()))
                .build();
        post = postRepository.save(post);
        return mapper.map(post, PostResponse.class);
    }


    @Override
    public List<PostResponse> findAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> (mapper.map(post, PostResponse.class)))
          //      .map(post->mapper.map(post.getComments().stream().map()))
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse findByPostId(Long postId) {
        return mapper.map(getPostFromDB(postId), PostResponse.class);
    }


    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = getPostFromDB(postId);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setShortDescription(postRequest.getShortDescription());
        post.setUrl(createUrl(postRequest.getTitle()));
        return mapper.map(postRepository.save(post), PostResponse.class);
    }

    @Override
    public MessageResponse deletePost(Long postId) {
        Post post = getPostFromDB(postId);
        postRepository.delete(post);
        return new MessageResponse("Post was deleted");
    }


    @Override
    public PostResponse findPostByUrl(String postUrl) {
        Post post = postRepository.findByUrl(postUrl)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with URL: %s not found", postUrl)));
        return mapper.map(post, PostResponse.class);
    }

    @Override
    public List<PostResponse> searchPosts(String query) {
        return postRepository.searchPosts(query)
                .stream()
                .map(post -> mapper.map(post, PostResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> findPostsByUser() {
        return null;
    }


    private Post getPostFromDB(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id: %s not found", postId)));
    }


    private String createUrl(String postTitle) {
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
