package com.api.paytree.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {

    /*
     * 서비스 타입
     * 1 : 지갑 서비스
     * 2 : 수기 결제 서비스
     * 3 : 카드 인증 서비스
     * 4 : 가상계좌 관리
     * 5 : 지갑 API 서비스
     */

    WALLET(1), PAYMENT(2), CARD(3), MANAGEMENT(4), API(5);

    private final int code;
}