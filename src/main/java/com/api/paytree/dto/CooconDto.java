package com.api.paytree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class CooconDto {
    @Getter
    @AllArgsConstructor
    public static class CooconList {
        List<Coocon> cooconList;
    }

    @Getter
    @AllArgsConstructor
    public static class CooconDetail {
        Coocon detail;
        List<Bulk> bulkList;
    }

    @Getter
    @Setter
    public static class Coocon {
        private String type;
        private String virtualName;
        private String virtualId;
        private String virtualAccount;
        private int balance;
        private int depositFee;
        private int withdrawFee;
        private boolean useYn;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class Bulk {
        private String virtualId;
        private String virtualAccount;
        private String memberName;
        private LocalDateTime createdAt;
    }
}