package com.michael.blog_mvc.payload.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class HttpResponse {

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss", timezone = "Israel")
    private Date timestamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String message;


    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String message) {
        timestamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
