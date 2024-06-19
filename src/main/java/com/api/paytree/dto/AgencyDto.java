package com.api.paytree.dto;

import com.api.paytree.utils.AccountStatus;
import com.api.paytree.utils.BusinessType;
import com.api.paytree.utils.ServiceType;
import com.api.paytree.utils.SettlementType;
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
        private List<Virtual> virtualList;
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
        private String virtualCode;
        private String virtualAccount;
        private String virtualName;
        private boolean virtualStatus;
        private int depositFee;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    public static class Search {
        private String agencyType; // 영업단 구분 : 총판, 대리점
        private String distributorId; // 충판 구분
        private AccountStatus status;
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
}