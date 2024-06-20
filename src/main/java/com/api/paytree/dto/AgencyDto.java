package com.api.paytree.dto;

import com.api.paytree.utils.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AgencyDto {
    @Getter
    @AllArgsConstructor
    public static class LoadFilter {
        private List<Distributor> distributorList;
        private Virtual virtualWallet;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Distributor {
        private String distributorId;
        private String distributorName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Virtual {
        private String virtualId;
        private String virtualCode;
        private String virtualName;
        private String virtualAccount;
        private boolean virtualStatus;
        private BigDecimal depositFee;
        private int balance;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    public static class Search {
        private String agencyType; // 영업단 구분 : 총판, 대리점
        private String distributorId; // 충판 구분
        private AccountStatus status;
        private String virtualId;
        private boolean virtualStatus;
        private String startDate;
        private String endDate;
        private SendType sendType;
        private HistoryType historyType;
        private String approvalStatus; // 대기, 완료, 오류, 실패, 취소
        private String filter; // accountId, accountName, serviceName, phoneNumber
        private String keyword;
        @Min(1)
        private int page;
        @Min(10)
        private int rows;
        private int offset;
    }

    @Getter
    @AllArgsConstructor
    public static class AgencyList {
        List<Agency> agencyList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Agency {
        private String accountId;
        private String agencyId;
        private String agencyName;
        private String upperAgencyId;
        private String upperAgencyName;
        private String serviceName;
        private AccountStatus status;
        private String agencyType;
        private String phoneNumber;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class WalletList {
        List<AgencyDetail> agencyWalletList;
    }

    @Getter
    @Setter
    public static class AgencyDetail {
        // 계정 정보
        @NotBlank
        private String agencyId;
        @NotBlank
        private String agencyName;
        @NotBlank
        private String agencyType;
        @NotBlank
        private String upperAgencyId;
        @NotBlank
        private String upperAgencyName;
        @NotBlank
        private String phoneNumber;
        @NotNull
        private AccountStatus status;
        @NotNull
        private LocalDateTime createdAt;

        // 상점 정보
        private BusinessType businessType;
        private String businessNumber;
        private String businessAddress;
        
        // 정산 정보
        private SettlementType settlementType;
        
        // 서비스 정보
        @NotNull
        private boolean isWithdrawal;
        @NotNull
        private ServiceType serviceType;
        @NotBlank
        private String serviceName;
        private String sms;
        private boolean manageVirtual;
        private String virtualId;
        private String virtualName;
        private String domain;
        @NotNull
        private boolean limitWithdrawal;
        private int limitWithdrawAmount;
        private int withdrawFee;
        private BigDecimal depositFee;
        
        // 대리점 전용
        private String walletId;
        private String walletAddress;
        private int balance;
        private String apiId;
        private String apiKey;
        private String apiIp;
        private int apiCount;
        private boolean useApi;
        private String callbackUrl;
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
        private String upperAgencyName;
        private String agencyType;
        private String agencyId;
        private String agencyName;
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
}