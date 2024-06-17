package com.api.paytree.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SendType {

    /*
     * 입출금 타입
     * 0 : 내부 송금
     * 1 : 은행 출금
     * 2 : 운영 수수료
     * 3 : 정산
     * 4 : 본사 수수료
     * 5 : 총판 수수료
     * 6 : 은행 바로 출금
     * 7 : 정산 차감
     * 8 : 수동 입금
     * 9 : 입금
     */

    INTERNAL_REMITTANCE(0), BANK_WITHDRAWAL(1), OPERATING_FEE(2), SETTLEMENT(3), HEADQUARTER_FEE(4), DISTRIBUTOR_FEE(5),
    DIRECT_WITHDRAWAL(6), DEDUCT_SETTLEMENT(7), MANUAL_DEPOSIT(8), DEPOSIT(9);

    private final int code;
}