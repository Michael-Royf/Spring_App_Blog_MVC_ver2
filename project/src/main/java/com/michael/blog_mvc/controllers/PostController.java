package com.michael.blog_mvc.controllers;

import com.michael.blog_mvc.payload.request.PostRequest;
import com.michael.blog_mvc.payload.response.PostResponse;
import com.michael.blog_mvc.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;





    @GetMapping("/admin/posts")
    public String getAllPosts(Model model){
        List<PostResponse> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }

    @GetMapping("/admin/posts/newpost")
    public String newPostForm(Model model){
        PostRequest postRequest = new PostRequest();
        model.addAttribute("post", postRequest);
        return "/admin/create_post";
    }

    @PostMapping("/admin/posts")
    public String createPost(@ModelAttribute("post") @Valid PostRequest postRequest,
                             BindingResult result,
                             Model model){
        if (result.hasErrors()){
            model.addAttribute("post", postRequest);
            return "/admin/create_post";
        }
        postService.creatPost(postRequest);
        return "redirect:/admin/posts";
    }


}
