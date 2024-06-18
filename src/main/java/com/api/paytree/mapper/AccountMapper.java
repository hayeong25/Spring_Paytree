package com.api.paytree.mapper;

import com.api.paytree.dto.AccountDto.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    int insertAccount(AccountDetail accountDetail);

    int updateAccount(AccountDetail accountDetail);
}