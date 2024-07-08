package com.api.paytree.service;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.SettlementDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.SettlementMapper;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Helper;
import com.api.paytree.utils.SettlementStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettlementService {
    @Autowired
    SettlementMapper settlementMapper;

    public SettlementList getSettlementList(Search search) {
        List<Settlement> settlementList;

        try {
            search.setOffset(Helper.calculateOffset(search.getPage(), search.getRows()));

            settlementList = Optional.of(settlementMapper.getSettlementList(search))
                                     .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_SETTLEMENT));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new SettlementList(settlementList);
    }

    public Settlement getSettlementDetail(String settlementId) {
        Settlement settlement;

        try {
            settlement = Optional.of(settlementMapper.getSettlementDetail(settlementId))
                                 .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_SETTLEMENT));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return settlement;
    }

    public Settlement updateSettlementStatus(ModifyStatus info) {
        Settlement settlement;

        try {
            settlement = Optional.of(settlementMapper.getSettlementDetail(info.getSettlementId()))
                                 .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_SETTLEMENT));

            switch (settlement.getSettlementStatus()) {
                case DEFER -> {
                    if (info.getSettlementStatus() == SettlementStatus.WAIT) {
                        throw new ClientException(ErrorCode.FORBIDDEN_SETTLEMENT);
                    }
                }
                case COMPLETE -> throw new ClientException(ErrorCode.FORBIDDEN_SETTLEMENT);
            }

            if (settlement.getSettlementAmount() != info.getSettlementAmount()) {
                throw new ClientException(ErrorCode.INVALID_PARAMETER);
            }

            Helper.checkUpdateResult(settlementMapper.updateSettlementStatus(info));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return settlement;
    }
}