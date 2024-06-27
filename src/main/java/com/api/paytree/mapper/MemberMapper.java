package com.api.paytree.mapper;

import com.api.paytree.dto.MemberDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> getMemberList(Search search);

    Member getMemberByMemberId(String memberId);

    List<WalletHistory> getMemberWalletHistoryByMemberId(String memberId);

    List<MemberHistory> getMemberHistory(String memberId);

    int updateMember(Member member);

    int insertMemberHistory(MemberHistory history);

    List<Member> getMemberWalletList(Search search);

    int updateMemberBalance(String memberId, int amount);

    int insertMemberWalletHistory(WalletHistory history);

    List<WalletHistory> getWalletHistoryList(Search search);

    WalletHistory getWalletHistoryDetail(String historyNo);
}