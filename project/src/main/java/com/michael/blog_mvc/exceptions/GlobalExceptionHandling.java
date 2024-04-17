package com.michael.blog_mvc.exceptions;

import com.michael.blog_mvc.payload.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {
//    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
//    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
//    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
//    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
//    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
//    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
//    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
//    public static final String ERROR_PATH = "/error";






    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("messages", errors);
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }




    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(
                httpStatus.value(),
                httpStatus,
                message),
                httpStatus);
    }
}
