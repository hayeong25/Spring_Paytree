package com.api.paytree.dto;

import com.api.paytree.utils.HistoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class WalletDto {
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
        private String virtualId;
        private String virtualName;
        private int sendType; // 수동 입금, 내부 전송, 가상계좌, 은행 바로 출금
        private HistoryType historyType; // 입출금 구분
        private String approvalStatus;
        private int amount;
        private int wireTransferFee;
        private String memo;
        private String tid;
        private LocalDateTime createdAt;
    }
}