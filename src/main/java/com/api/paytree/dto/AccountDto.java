package com.api.paytree.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class AccountDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class AccountDetail {
        private String accountId;
        private String accountName;
        private String password;
        private int status;
        private String accountType;
        private String phoneNumber;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}