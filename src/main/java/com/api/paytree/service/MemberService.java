package com.api.paytree.service;

import com.api.paytree.dto.MemberDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.MemberMapper;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;

    private static final String ACCOUNT = "account";

    public MemberList getMemberList(Search search) {
        List<Member> memberList;

        try {
            search.setOffset(calculateOffset(search.getPage(), search.getRows()));

            memberList = Optional.of(memberMapper.getMemberList(search))
                                 .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new MemberList(memberList);
    }

    public MemberDetail getMemberDetail(String memberId) {
        Member member;
        List<MemberHistory> memberHistoryList;
        List<WalletHistory> walletHistoryList;

        try {
            member = Optional.of(memberMapper.getMemberByMemberId(memberId))
                             .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));

            walletHistoryList = Optional.of(memberMapper.getMemberWalletHistoryByMemberId(memberId))
                                        .orElseThrow(() -> new ClientException(ErrorCode.INVALID_WALLET));

            memberHistoryList = Optional.of(memberMapper.getMemberHistory(memberId))
                                        .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new MemberDetail(member, memberHistoryList, walletHistoryList);
    }

    public MemberDetail modifyMember(Member member) {
        List<MemberHistory> memberHistoryList;
        List<WalletHistory> walletHistoryList;

        try {
            checkUpdateResult(memberMapper.updateMember(member));

            MemberHistory memberHistory = MemberHistory.builder()
                                                       .memberId(member.getMemberId())
                                                       .memberType(Role.USER.getCode())
                                                       .history(member.toString())
                                                       .historyType(ACCOUNT)
                                                       .createdAt(LocalDateTime.now())
                                                       .build();

            checkInsertResult(memberMapper.insertMemberHistory(memberHistory));

            member = Optional.of(memberMapper.getMemberByMemberId(member.getMemberId()))
                             .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));

            walletHistoryList = Optional.of(memberMapper.getMemberWalletHistoryByMemberId(member.getMemberId()))
                                        .orElseThrow(() -> new ClientException(ErrorCode.INVALID_WALLET));

            memberHistoryList = Optional.of(memberMapper.getMemberHistory(member.getMemberId()))
                                        .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new MemberDetail(member, memberHistoryList, walletHistoryList);
    }

    private int calculateOffset(int page, int rows) {
        return page * rows - rows;
    }

    private void checkInsertResult(int result) {
        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }
    }

    private void checkUpdateResult(int result) {
        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }
    }
}