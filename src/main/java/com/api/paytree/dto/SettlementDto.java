package com.api.paytree.dto;

import com.api.paytree.utils.SettlementStatus;
import com.api.paytree.utils.SettlementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class SettlementDto {
    @Getter
    @AllArgsConstructor
    public static class SettlementList {
        List<Settlement> settlementList;
    }

    @Getter
    @Setter
    public static class Settlement {
        private String settlementId;
        private String settlementDate;
        private SettlementStatus settlementStatus;
        private SettlementType settlementType;
        private String serviceName;
        private String virtualId;
        private String upperAgencyId;
        private String upperAgencyName;
        private String agencyId;
        private String agencyName;
        private String memberId;
        private String memberName;
        private int depositAmount;
        private int fee;
        private int settlementAmount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    public static class ModifyStatus {
        @NotBlank
        private String settlementId;
        @NotNull
        private SettlementStatus settlementStatus;
        @Min(1)
        private int settlementAmount;
        private String reason; // 보류 사유
    }
}