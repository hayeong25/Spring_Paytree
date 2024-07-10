package com.api.paytree.service;

import com.api.paytree.dto.StatisticsDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.StatisticsMapper;
import com.api.paytree.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {
    @Autowired
    StatisticsMapper statisticsMapper;

    public StatisticsList getMonthlyStatistics(String targetMonth) {
        List<Statistics> statisticsList;

        try {
            statisticsList = Optional.of(statisticsMapper.getMonthlyStatistics(targetMonth))
                                     .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new StatisticsList(statisticsList);
    }
}