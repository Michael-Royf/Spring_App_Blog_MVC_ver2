package com.michael.blog_mvc.controllers;


import com.michael.blog_mvc.payload.request.CommentRequest;
import com.michael.blog_mvc.payload.response.PostResponse;
import com.michael.blog_mvc.service.CommentService;
import com.michael.blog_mvc.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostResponse> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/blog/view_posts";
    }


    @GetMapping("/post/{postUrl}")
    public String showPost(@PathVariable("postUrl") String postUrl,
                           Model model) {
        PostResponse post = postService.findPostByUrl(postUrl);
        CommentRequest comment = new CommentRequest();
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        return "/blog/blog_post";
    }


    @GetMapping("/page/search")//?query=java
    public String searchPost(@RequestParam("query") String query, Model model) {
        List<PostResponse> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "/blog/view_posts";
    }


}
