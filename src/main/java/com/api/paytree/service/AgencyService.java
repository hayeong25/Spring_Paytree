package com.api.paytree.service;

import com.api.paytree.dto.AccountDto.*;
import com.api.paytree.dto.AgencyDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.AccountMapper;
import com.api.paytree.mapper.AgencyMapper;
import com.api.paytree.utils.AccountStatus;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgencyService {
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AgencyMapper agencyMapper;

    public LoadFilter getListFilter() {
        List<Distributor> distributorList;
        List<Virtual> virtualList;

        try {
            distributorList = Optional.of(agencyMapper.getDistributorListFilter())
                                      .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));

            virtualList = Optional.of(agencyMapper.getVirtualListFilter())
                                  .orElseThrow(() -> new ClientException(ErrorCode.SELECT_FAIL));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new LoadFilter(distributorList, virtualList);
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