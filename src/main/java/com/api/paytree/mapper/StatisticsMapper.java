package com.api.paytree.mapper;

import com.api.paytree.dto.StatisticsDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    List<Statistics> getMonthlyStatistics(String targetMonth);
}