package com.api.paytree.dto;

import com.api.paytree.utils.HistoryType;
import com.api.paytree.utils.SendType;
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
        List<WalletHistory> walletHistoryList;
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
    @Setter
    public static class Search {
        private String upperAgencyId;
        private String agencyId;
        private int status;
        private SendType sendType;
        private HistoryType historyType;
        private String approvalStatus; // 대기, 완료, 오류, 실패, 취소
        private String filter; // memberId, memberName, phoneNumber
        private String keyword;
        @Min(1)
        private int page;
        @Min(10)
        private int rows;
        private int offset;
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

    @Getter
    @AllArgsConstructor
    public static class HistoryList {
        List<WalletHistory> historyList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class WalletHistory {
        private String historyNo;
        private String upperAgencyId;
        private String upperAgencyName;
        private String agencyType;
        private String agencyId;
        private String agencyName;
        private String memberId;
        private String memberName;
        private String virtualName;
        private int sendType; // 수동 입금, 내부 전송, 가상계좌, 은행 바로 출금
        private String historyType; // 입출금 구분
        private String approvalStatus;
        private int amount;
        private int wireTransferFee;
        private String memo;
        private String tid;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class SendWallet {
        @NotBlank
        private String sendAccountId;
        @NotBlank
        private String receiveAccountId;
        @Min(1)
        private int sendAmount;
        private String memo;
    }
}