package com.michael.blog_mvc.controllers;

import com.michael.blog_mvc.payload.request.PostRequest;
import com.michael.blog_mvc.payload.response.CommentResponse;
import com.michael.blog_mvc.payload.response.PostResponse;
import com.michael.blog_mvc.service.CommentService;
import com.michael.blog_mvc.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final CommentService commentService;
    private final ModelMapper mapper;


    @GetMapping("/admin/posts")
    public String getAllPosts(Model model) {
        List<PostResponse> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }


    @GetMapping("/admin/posts/newpost")
    public String newPostForm(Model model) {
        PostRequest postRequest = new PostRequest();
        model.addAttribute("post", postRequest);
        return "/admin/create_post";
    }


    @PostMapping("/admin/posts")
    public String createPost(@ModelAttribute("post") @Valid PostRequest postRequest,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("post", postRequest);
            return "/admin/create_post";
        }
        postService.creatPost(postRequest);
        return "redirect:/admin/posts";
    }


    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId,
                               Model model) {
        PostResponse post = postService.findByPostId(postId);
        model.addAttribute("post", post);
        return "/admin/edit_post";
    }

    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @ModelAttribute("post") @Valid PostRequest postRequest,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("post", postRequest);
            return "/admin/edit_post";
        }
        postService.updatePost(postId, postRequest);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postsId}/delete")
    public String deletePost(@PathVariable("postsId") Long postsId) {
        postService.deletePost(postsId);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model) {
        PostResponse postResponse = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postResponse);
        return "/admin/view_post";
    }

    //localhost:8080/admin/posts/search?query=java
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model) {
        List<PostResponse> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }


    @GetMapping("/admin/posts/comments")
    public String postComments(Model model) {
        List<CommentResponse> comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        return "/admin/comments";
    }

    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }

}
