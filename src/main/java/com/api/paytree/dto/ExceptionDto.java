package com.api.paytree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private HttpStatus status;
    private String code;
    private String message;
}