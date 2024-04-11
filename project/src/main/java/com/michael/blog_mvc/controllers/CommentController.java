package com.michael.blog_mvc.controllers;

import com.michael.blog_mvc.payload.request.CommentRequest;
import com.michael.blog_mvc.payload.response.PostResponse;
import com.michael.blog_mvc.service.CommentService;
import com.michael.blog_mvc.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/{postUrl}/comments")
    public String createComment(@PathVariable("postUrl") String postUrl,
                                @ModelAttribute("comment") @Valid CommentRequest commentRequest,
                                BindingResult result,
                                Model model) {

        PostResponse post = postService.findPostByUrl(postUrl);
        if(result.hasErrors()){
            model.addAttribute("post", post);
            model.addAttribute("comment", commentRequest);
            return "/blog/blog_post";
        }

        commentService.createComment(postUrl, commentRequest);


        return "redirect:/post/" + postUrl;
    }


}
