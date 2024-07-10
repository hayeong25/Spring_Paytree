package com.api.paytree.controller;

import com.api.paytree.dto.StatisticsDto.*;
import com.api.paytree.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * 통계 관리
 * 1. 월별 통계
 */

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    // POST : 월별 통계
    @PostMapping("/monthly")
    public ResponseEntity<StatisticsList> getMonthlyStatistics(@RequestParam String targetMonth) {
        return ResponseEntity.ok(statisticsService.getMonthlyStatistics(targetMonth));
    }
}