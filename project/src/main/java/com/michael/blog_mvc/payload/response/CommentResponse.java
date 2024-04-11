package com.michael.blog_mvc.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {
    private Long id;
    private String name;
    private String email;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
