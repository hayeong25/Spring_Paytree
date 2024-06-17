package com.api.paytree.exception;

import com.api.paytree.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ExceptionDto> clientExceptionHandle(ClientException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                             .body(ExceptionDto.builder()
                                               .status(ex.getErrorCode().getStatus())
                                               .code(ex.getErrorCode().getCode())
                                               .message(ex.getErrorCode().getMessage())
                                               .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> exceptionHandle() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ExceptionDto.builder()
                                               .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                               .code("")
                                               .message("TIME OUT")
                                               .build());
    }
}