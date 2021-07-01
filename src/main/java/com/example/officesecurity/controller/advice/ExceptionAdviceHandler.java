package com.example.officesecurity.controller.advice;

import com.example.officesecurity.exeptions.BadRequestException;
import com.example.officesecurity.model.ResponseMessage;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviceHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseMessage> customerNotFoundExceptionHandler(BadRequestException e) {
        String message = e.getMessage();
        ResponseMessage responseMessage = ResponseMessage.builder().message(message).build();
        return ResponseEntity.badRequest().body(responseMessage);

    }
}
