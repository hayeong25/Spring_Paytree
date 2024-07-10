package com.api.paytree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class StatisticsDto {
    @Getter
    @AllArgsConstructor
    public static class StatisticsList {
        List<Statistics> statisticsList;
    }

    @Getter
    @Setter
    public static class Statistics {
        private String date;
        private int upperAgencyCount;
        private int agencyCount;
        private int memberCount;
        private int passCount;
        private int kakaoCount;
        private int talkCount;
        private int withdrawCount;
    }
}