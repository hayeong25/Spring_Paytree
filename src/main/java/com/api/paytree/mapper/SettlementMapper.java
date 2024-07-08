package com.api.paytree.mapper;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.SettlementDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SettlementMapper {
    List<Settlement> getSettlementList(Search search);

    Settlement getSettlementDetail(String settlementId);

    int updateSettlementStatus(ModifyStatus info);
}