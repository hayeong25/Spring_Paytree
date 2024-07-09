package com.api.paytree.service;

import com.api.paytree.dto.WalletDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Helper;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    public WalletHistory registerBalance(WalletHistory info) {
        try {
            Helper.insertWalletHistory(info);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return info;
    }
}