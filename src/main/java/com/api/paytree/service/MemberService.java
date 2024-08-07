package com.api.paytree.service;

import com.api.paytree.dto.AgencyDto;
import com.api.paytree.dto.MemberDto.*;
import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.WalletDto;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.AgencyMapper;
import com.api.paytree.mapper.HistoryMapper;
import com.api.paytree.mapper.MemberMapper;
import com.api.paytree.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Autowired
    AgencyMapper agencyMapper;

    @Autowired
    HistoryMapper historyMapper;

    private static final String ACCOUNT = "account";
    private static final String SUCCESS = "완료";
    private static final String SEND = "send_";
    private static final String RETURN = "return_";

    public MemberList getMemberList(Search search) {
        List<Member> memberList;

        try {
            search.setOffset(Helper.calculateOffset(search.getPage(), search.getRows()));

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
        List<WalletDto.WalletHistory> walletHistoryList;

        try {
            member = Optional.of(memberMapper.getMemberByMemberId(memberId))
                             .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));

            walletHistoryList = Optional.of(historyMapper.getMemberWalletHistoryByMemberId(memberId))
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
        List<WalletDto.WalletHistory> walletHistoryList;

        try {
            Helper.checkUpdateResult(memberMapper.updateMember(member));

            MemberHistory memberHistory = MemberHistory.builder()
                                                       .memberId(member.getMemberId())
                                                       .memberType(Role.USER.getCode())
                                                       .history(member.toString())
                                                       .historyType(ACCOUNT)
                                                       .createdAt(LocalDateTime.now())
                                                       .build();

            Helper.checkInsertResult(memberMapper.insertMemberHistory(memberHistory));

            member = Optional.of(memberMapper.getMemberByMemberId(member.getMemberId()))
                             .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));

            walletHistoryList = Optional.of(historyMapper.getMemberWalletHistoryByMemberId(member.getMemberId()))
                                        .orElseThrow(() -> new ClientException(ErrorCode.INVALID_WALLET));

            memberHistoryList = Optional.of(memberMapper.getMemberHistory(member.getMemberId()))
                                        .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new MemberDetail(member, memberHistoryList, walletHistoryList);
    }

    public WalletList getMemberWalletList(Search search) {
        List<Member> memberWalletList;

        try {
            search.setOffset(Helper.calculateOffset(search.getPage(), search.getRows()));

            memberWalletList = Optional.of(memberMapper.getMemberWalletList(search))
                                       .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new WalletList(memberWalletList);
    }

    public WalletDto.SendWallet sendToMemberWallet(WalletDto.SendWallet sendInfo) {
        try {
            // [1] agency balance 확인
            AgencyDto.AgencyDetail agency = Optional.of(agencyMapper.getAgencyDetail(sendInfo.getSendAccountId()))
                                                    .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_AGENCY));

            if (agency.getBalance() < sendInfo.getSendAmount()) {
                throw new ClientException(ErrorCode.NOT_ENOUGH_BALANCE);
            }

            Member member = Optional.of(memberMapper.getMemberByMemberId(sendInfo.getReceiveAccountId()))
                                    .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_ACCOUNT));

            // [2] 영업점 잔고 차감
            Helper.updateAgencyBalance(sendInfo.getSendAccountId(), agency.getBalance() - sendInfo.getSendAmount());

            // [3] 회원 잔액 업데이트
            Helper.updateMemberBalance(sendInfo.getReceiveAccountId(), member.getBalance() + sendInfo.getSendAmount());

            // [4] 영업점 지갑 히스토리 추가
            WalletDto.WalletHistory agencyHistory = WalletDto.WalletHistory.builder()
                                                                           .upperAgencyName(agency.getUpperAgencyName())
                                                                           .agencyType(agency.getAgencyType())
                                                                           .agencyId(sendInfo.getSendAccountId())
                                                                           .agencyName(agency.getAgencyName())
                                                                           .virtualId(VirtualAccount.COOCON.getCode())
                                                                           .virtualName(agency.getVirtualName())
                                                                           .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                                           .historyType(HistoryType.WITHDRAW)
                                                                           .approvalStatus(SUCCESS)
                                                                           .amount(sendInfo.getSendAmount() * -1)
                                                                           .wireTransferFee(0)
                                                                           .memo(sendInfo.getMemo())
                                                                           .tid(SEND + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                                           .createdAt(LocalDateTime.now())
                                                                           .build();

            Helper.insertWalletHistory(agencyHistory);

            // [5] 회원 지갑 히스토리 추가
            WalletDto.WalletHistory memberHistory = WalletDto.WalletHistory.builder()
                                                                           .upperAgencyName(member.getUpperAgencyName())
                                                                           .agencyType(agency.getAgencyType())
                                                                           .agencyId(sendInfo.getSendAccountId())
                                                                           .agencyName(member.getAgencyName())
                                                                           .virtualId(VirtualAccount.COOCON.getCode())
                                                                           .virtualName(member.getVirtualName())
                                                                           .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                                           .historyType(HistoryType.DEPOSIT)
                                                                           .approvalStatus(SUCCESS)
                                                                           .amount(sendInfo.getSendAmount())
                                                                           .wireTransferFee(0)
                                                                           .memo(sendInfo.getMemo())
                                                                           .tid(SEND + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                                           .createdAt(LocalDateTime.now())
                                                                           .build();

            Helper.insertWalletHistory(memberHistory);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return sendInfo;
    }

    public WalletDto.SendWallet returnToAgencyWallet(WalletDto.SendWallet sendInfo) {
        try {
            // [1] member balance 확인
            Member member = Optional.of(memberMapper.getMemberByMemberId(sendInfo.getSendAccountId()))
                                    .orElseThrow(() -> new ClientException(ErrorCode.SERVER_ERROR));

            if (member.getBalance() < sendInfo.getSendAmount()) {
                throw new ClientException(ErrorCode.NOT_ENOUGH_BALANCE);
            }

            // [2] 회원 잔고 차감
            Helper.updateMemberBalance(sendInfo.getSendAccountId(), member.getBalance() - sendInfo.getSendAmount());

            // [3] 영업점 잔액 업데이트
            AgencyDto.AgencyDetail agency = Optional.of(agencyMapper.getAgencyDetail(sendInfo.getSendAccountId()))
                                                    .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_AGENCY));

            Helper.updateAgencyBalance(sendInfo.getReceiveAccountId(), agency.getBalance() + sendInfo.getSendAmount());

            // [4] 회원 지갑 히스토리 추가
            WalletDto.WalletHistory memberHistory = WalletDto.WalletHistory.builder()
                                                                           .upperAgencyName(member.getUpperAgencyName())
                                                                           .agencyType(agency.getAgencyType())
                                                                           .agencyId(sendInfo.getSendAccountId())
                                                                           .agencyName(member.getAgencyName())
                                                                           .virtualId(VirtualAccount.COOCON.getCode())
                                                                           .virtualName(member.getVirtualName())
                                                                           .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                                           .historyType(HistoryType.WITHDRAW)
                                                                           .approvalStatus(SUCCESS)
                                                                           .amount(sendInfo.getSendAmount() * -1)
                                                                           .wireTransferFee(0)
                                                                           .memo(sendInfo.getMemo())
                                                                           .tid(RETURN + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                                           .createdAt(LocalDateTime.now())
                                                                           .build();

            Helper.insertWalletHistory(memberHistory);

            // [5] 영업점 지갑 히스토리 추가
            WalletDto.WalletHistory agencyHistory = WalletDto.WalletHistory.builder()
                                                                           .upperAgencyName(agency.getUpperAgencyName())
                                                                           .agencyType(agency.getAgencyType())
                                                                           .agencyId(sendInfo.getSendAccountId())
                                                                           .agencyName(agency.getAgencyName())
                                                                           .virtualId(VirtualAccount.COOCON.getCode())
                                                                           .virtualName(agency.getVirtualName())
                                                                           .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                                           .historyType(HistoryType.DEPOSIT)
                                                                           .approvalStatus(SUCCESS)
                                                                           .amount(sendInfo.getSendAmount())
                                                                           .wireTransferFee(0)
                                                                           .memo(sendInfo.getMemo())
                                                                           .tid(RETURN + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                                           .createdAt(LocalDateTime.now())
                                                                           .build();

            Helper.insertWalletHistory(agencyHistory);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return sendInfo;
    }
}