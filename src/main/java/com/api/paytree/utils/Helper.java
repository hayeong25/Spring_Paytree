package com.api.paytree.utils;

import com.api.paytree.dto.AgencyDto;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.AgencyMapper;
import com.api.paytree.mapper.MemberMapper;

public class Helper {
    static AgencyMapper agencyMapper;
    static MemberMapper memberMapper;

    public static void updateAdminBalance(int amount) {
        checkUpdateResult(agencyMapper.updateAdminBalance(amount));
    }

    public static void updateAgencyBalance(String agencyId, int amount) {
        checkUpdateResult(agencyMapper.updateAgencyBalance(agencyId, amount));
    }

    public static void updateMemberBalance(String memberId, int amount) {
        checkUpdateResult(memberMapper.updateMemberBalance(memberId, amount));
    }

    public static void insertWalletHistory(AgencyDto.WalletHistory history) {
        checkInsertResult(agencyMapper.insertWalletHistory(history));
    }

    public static void checkInsertResult(int result) {
        if (result < 1) {
            throw new ClientException(ErrorCode.INSERT_FAIL);
        }
    }

    public static void checkUpdateResult(int result) {
        if (result < 1) {
            throw new ClientException(ErrorCode.UPDATE_FAIL);
        }
    }

    public static int calculateOffset(int page, int rows) {
        return page * rows - rows;
    }
}
