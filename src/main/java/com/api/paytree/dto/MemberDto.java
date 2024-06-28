package com.api.paytree.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class MemberList {
        List<Member> memberList;
    }

    @Getter
    @AllArgsConstructor
    public static class MemberDetail {
        Member member;
        List<MemberHistory> memberHistoryList;
        List<WalletDto.WalletHistory> walletHistoryList;
    }

    @Getter
    @Setter
    @ToString
    public static class Member {
        @NotBlank
        private String memberId;
        @NotBlank
        private String memberName;
        private String memberType;
        @NotBlank
        private String agencyId;
        @NotBlank
        private String agencyName;
        @NotBlank
        private String upperAgencyId;
        @NotBlank
        private String upperAgencyName;
        @NotBlank
        private String phoneNumber;
        @NotBlank
        private String birth;
        @Min(0)
        private int status;
        private String bankName;
        private String accountNumber;
        private String accountHolder;
        private String virtualId;
        private String virtualName;
        private String virtualAccount;
        private int balance;
        private String socialStatus;
        private String address;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberHistory {
        private String memberId;
        private String memberType;
        private String history;
        private String historyType;
        private LocalDateTime createdAt;
    }

    @Getter
    @AllArgsConstructor
    public static class WalletList {
        List<Member> memberWalletList;
    }
}