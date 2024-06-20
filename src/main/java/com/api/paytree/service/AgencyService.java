package com.api.paytree.service;

import com.api.paytree.dto.AccountDto.*;
import com.api.paytree.dto.AgencyDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.AccountMapper;
import com.api.paytree.mapper.AgencyMapper;
import com.api.paytree.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AgencyService {
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AgencyMapper agencyMapper;

    private static final String COMPANY = "company";
    private static final String HEADQUARTER = "본사";
    private static final String SUCCESS = "완료";
    private static final String SEND = "send_";
    private static final String RETURN = "return_";

    public LoadFilter getListFilter() {
        List<Distributor> distributorList;
        Virtual virtualWallet;

        try {
            distributorList = Optional.of(agencyMapper.getDistributorListFilter())
                                      .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));

            virtualWallet = Optional.of(agencyMapper.getVirtualWalletFilter())
                                  .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new LoadFilter(distributorList, virtualWallet);
    }

    public AgencyList getAgencyList(Search search) {
        List<Agency> agencyList;

        try {
            search.setOffset(calculateOffset(search.getPage(), search.getRows()));

            agencyList = Optional.of(agencyMapper.getAgencyList(search))
                                 .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new AgencyList(agencyList);
    }

    public AgencyDetail getAgencyDetail(String agencyId) {
        AgencyDetail agencyDetail;

        try {
            agencyDetail = Optional.of(agencyMapper.getAgencyDetail(agencyId))
                                   .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return agencyDetail;
    }

    public AgencyList getBelongAgencyList(Role agencyType) {
        List<Agency> agencyList;

        try {
            if (agencyType == Role.DISTRIBUTOR || agencyType == Role.AGENCY) {
                agencyList = Optional.of(agencyMapper.getBelongAgencyList(agencyType.getCode()))
                                     .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
            } else {
                throw new ClientException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new AgencyList(agencyList);
    }

    @Transactional
    public AgencyDetail registerAgency(AgencyDetail agencyDetail) {
        try {
            AccountDetail accountDetail = AccountDetail.builder()
                                                       .accountId(agencyDetail.getAgencyId())
                                                       .accountName(agencyDetail.getAgencyName())
                                                       .password(null)
                                                       .status(AccountStatus.WAIT)
                                                       .accountType(agencyDetail.getAgencyType())
                                                       .phoneNumber(agencyDetail.getPhoneNumber())
                                                       .createdAt(LocalDateTime.now())
                                                       .modifiedAt(LocalDateTime.now())
                                                       .build();

            checkInsertResult(accountMapper.insertAccount(accountDetail));
            checkInsertResult(agencyMapper.insertAgency(agencyDetail));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return agencyDetail;
    }

    public AgencyDetail modifyAgency(AgencyDetail agencyDetail) {
        try {
            AccountDetail accountDetail = AccountDetail.builder()
                                                       .accountId(agencyDetail.getAgencyId())
                                                       .accountName(agencyDetail.getAgencyName())
                                                       .status(agencyDetail.getStatus())
                                                       .phoneNumber(agencyDetail.getPhoneNumber())
                                                       .modifiedAt(LocalDateTime.now())
                                                       .build();

            checkUpdateResult(accountMapper.updateAccount(accountDetail));
            checkUpdateResult(agencyMapper.updateAgency(agencyDetail));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return agencyDetail;
    }

    public WalletList getAgencyWalletList(Search search) {
        List<AgencyDetail> agencyWalletList;

        try {
            search.setOffset(calculateOffset(search.getPage(), search.getRows()));

            agencyWalletList = Optional.of(agencyMapper.getAgencyWalletList(search))
                                       .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new WalletList(agencyWalletList);
    }

    @Transactional
    public SendWallet sendToAgencyWallet(SendWallet sendInfo) {
        try {
            int validBalance = Optional.of(agencyMapper.getAdminBalance())
                                       .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));

            if (validBalance < sendInfo.getSendAmount()) {
                throw new ClientException(ErrorCode.NOT_ENOUGH_BALANCE);
            }
            
            AgencyDetail agencyDetail = Optional.of(agencyMapper.getAgencyDetail(sendInfo.getReceiveAccountId()))
                                                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_AGENCY));

            // 본사 잔고 차감
            updateAdminBalance(validBalance - sendInfo.getSendAmount());

            // 영업점 잔액 업데이트
            updateAgencyBalance(sendInfo.getReceiveAccountId(), agencyDetail.getBalance() + sendInfo.getSendAmount());
            
            // 지갑 히스토리 추가
            WalletHistory adminHistory = WalletHistory.builder()
                                                      .upperAgencyName(COMPANY)
                                                      .agencyType(HEADQUARTER)
                                                      .agencyId(sendInfo.getSendAccountId())
                                                      .agencyName(COMPANY)
                                                      .virtualName(agencyDetail.getVirtualName())
                                                      .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                      .historyType(HistoryType.WITHDRAW.name())
                                                      .approvalStatus(SUCCESS)
                                                      .amount(sendInfo.getSendAmount() * -1)
                                                      .wireTransferFee(0)
                                                      .memo(sendInfo.getMemo())
                                                      .tid(SEND + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                      .createdAt(LocalDateTime.now())
                                                      .build();

            insertWalletHistory(adminHistory);

            WalletHistory agencyHistory = WalletHistory.builder()
                                                      .upperAgencyName(agencyDetail.getUpperAgencyName())
                                                      .agencyType(agencyDetail.getAgencyType())
                                                      .agencyId(sendInfo.getSendAccountId())
                                                      .agencyName(agencyDetail.getAgencyName())
                                                      .virtualName(agencyDetail.getVirtualName())
                                                      .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                      .historyType(HistoryType.DEPOSIT.name())
                                                      .approvalStatus(SUCCESS)
                                                      .amount(sendInfo.getSendAmount())
                                                      .wireTransferFee(0)
                                                      .memo(sendInfo.getMemo())
                                                      .tid(SEND + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                      .createdAt(LocalDateTime.now())
                                                      .build();

            insertWalletHistory(agencyHistory);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return sendInfo;
    }

    public SendWallet returnToAdminWallet(SendWallet sendInfo) {
        try {
            AgencyDetail agencyDetail = Optional.of(agencyMapper.getAgencyDetail(sendInfo.getReceiveAccountId()))
                                                .orElseThrow(() -> new ClientException(ErrorCode.NOT_FOUNDED_AGENCY));

            if (agencyDetail.getBalance() < sendInfo.getSendAmount()) {
                throw new ClientException(ErrorCode.NOT_ENOUGH_BALANCE);
            }

            int validBalance = Optional.of(agencyMapper.getAdminBalance())
                                       .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));

            // 영업점 잔고 차감
            updateAgencyBalance(sendInfo.getReceiveAccountId(), agencyDetail.getBalance() - sendInfo.getSendAmount());

            // 본사 잔고 업데이트
            updateAdminBalance(validBalance + sendInfo.getSendAmount());

            // 지갑 히스토리 추가
            WalletHistory agencyHistory = WalletHistory.builder()
                                                       .upperAgencyName(agencyDetail.getUpperAgencyName())
                                                       .agencyType(agencyDetail.getAgencyType())
                                                       .agencyId(sendInfo.getSendAccountId())
                                                       .agencyName(agencyDetail.getAgencyName())
                                                       .virtualName(agencyDetail.getVirtualName())
                                                       .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                       .historyType(HistoryType.WITHDRAW.name())
                                                       .approvalStatus(SUCCESS)
                                                       .amount(sendInfo.getSendAmount() * -1)
                                                       .wireTransferFee(0)
                                                       .memo(sendInfo.getMemo())
                                                       .tid(RETURN + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                       .createdAt(LocalDateTime.now())
                                                       .build();

            insertWalletHistory(agencyHistory);

            WalletHistory adminHistory = WalletHistory.builder()
                                                      .upperAgencyName(COMPANY)
                                                      .agencyType(HEADQUARTER)
                                                      .agencyId(sendInfo.getSendAccountId())
                                                      .agencyName(COMPANY)
                                                      .virtualName(agencyDetail.getVirtualName())
                                                      .sendType(SendType.INTERNAL_REMITTANCE.getCode())
                                                      .historyType(HistoryType.DEPOSIT.name())
                                                      .approvalStatus(SUCCESS)
                                                      .amount(sendInfo.getSendAmount())
                                                      .wireTransferFee(0)
                                                      .memo(sendInfo.getMemo())
                                                      .tid(RETURN + LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                                                      .createdAt(LocalDateTime.now())
                                                      .build();

            insertWalletHistory(adminHistory);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return sendInfo;
    }

    public HistoryList getWalletHistoryList(Search search) {
        List<WalletHistory> historyList;

        try {
            search.setOffset(calculateOffset(search.getPage(), search.getRows()));

            historyList = Optional.ofNullable(agencyMapper.getWalletHistoryList(search))
                                  .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new HistoryList(historyList);
    }

    public WalletHistory getWalletHistoryDetail(String historyNo) {
        WalletHistory historyDetail;

        try {
            historyDetail = Optional.of(agencyMapper.getWalletHistoryDetail(historyNo))
                                    .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return historyDetail;
    }

    private void updateAdminBalance(int amount) {
        checkUpdateResult(agencyMapper.updateAdminBalance(amount));
    }

    private void updateAgencyBalance(String agencyId, int amount) {
        checkUpdateResult(agencyMapper.updateAgencyBalance(agencyId, amount));
    }

    private void insertWalletHistory(WalletHistory history) {
        checkInsertResult(agencyMapper.insertWalletHistory(history));
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