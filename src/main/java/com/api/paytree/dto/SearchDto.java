package com.api.paytree.dto;

import com.api.paytree.utils.HistoryType;
import com.api.paytree.utils.SendType;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

public class SearchDto {
    @Getter
    @Setter
    public static class Search {
        private String startDate;
        private String endDate;
        private String upperAgencyId;
        private String agencyId;
        private String agencyType;
        private String virtualId;
        private boolean virtualStatus;
        private SendType sendType;
        private HistoryType historyType;
        private int status;
        private int approvalStatus; // 대기, 완료, 오류, 실패, 취소
        private String filter; // agencyId, agencyName, serviceName, agencyPhoneNumber, memberId, memberName, memberPhoneNumber
        private String keyword;
        @Min(1)
        private int page;
        @Min(10)
        private int rows;
        private int offset;
    }
}