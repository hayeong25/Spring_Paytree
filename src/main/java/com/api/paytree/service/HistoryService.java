package com.api.paytree.service;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.WalletDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.HistoryMapper;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {
    @Autowired
    HistoryMapper historyMapper;

    public HistoryList getWalletHistoryList(Search search) {
        List<WalletHistory> historyList;

        try {
            search.setOffset(Helper.calculateOffset(search.getPage(), search.getRows()));

            historyList = Optional.ofNullable(historyMapper.getWalletHistoryList(search))
                                  .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new HistoryList(historyList);
    }

    public WalletHistory getWalletHistoryDetail(String historyNo) {
        WalletHistory historyDetail;

        try {
            historyDetail = Optional.of(historyMapper.getWalletHistoryDetail(historyNo))
                                    .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return historyDetail;
    }
}