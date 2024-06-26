package com.michael.blog_mvc.payload.response;

import com.michael.blog_mvc.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String url;
    private String content;
    private String shortDescription;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Set<Comment> comments;
}

