package com.michael.blog_mvc.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostRequest {
    //  private Long id;
    @NotEmpty(message = "Title should not be empty ")
    private String title;
    @NotEmpty(message = "Short description should not be empty")
    private String shortDescription;
    @NotEmpty(message = "Content should not be empty")
    private String content;

}
