package com.api.paytree.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    /*
     * MANAGER : 시스템 관리자
     * ADMIN : 티온 본사
     * DISTRIBUTOR : 총판
     * AGENCY : 대리점
     * USER : 회원
     */

    MANAGER("A001000"), ADMIN("A001001"), DISTRIBUTOR("A001002"), AGENCY("A001003"), USER("A001005");

    private final String code;
}