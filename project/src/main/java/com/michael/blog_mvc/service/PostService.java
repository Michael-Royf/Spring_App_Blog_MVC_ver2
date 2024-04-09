package com.michael.blog_mvc.service;

import com.michael.blog_mvc.payload.request.PostRequest;
import com.michael.blog_mvc.payload.response.MessageResponse;
import com.michael.blog_mvc.payload.response.PostResponse;

import java.util.List;

public interface PostService {



    PostResponse creatPost(PostRequest postRequest);

    List<PostResponse> findAllPosts();

    PostResponse findByPostId(Long postId);

    PostResponse updatePost(Long postId, PostRequest postRequest);

    MessageResponse deletePost(Long postId);

    PostResponse findPostByUrl(String postUrl);

    List<PostResponse> searchPosts(String query);

    List<PostResponse> findPostsByUser();
}
