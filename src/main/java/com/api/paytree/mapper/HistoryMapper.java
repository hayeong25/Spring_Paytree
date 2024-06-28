package com.api.paytree.mapper;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.WalletDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {
    List<WalletHistory> getMemberWalletHistoryByMemberId(String memberId);

    int insertWalletHistory(WalletHistory history);

    List<WalletHistory> getWalletHistoryList(Search search);

    WalletHistory getWalletHistoryDetail(String historyNo);
}