package com.api.paytree.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final Map<String, Role> ROLE_MAP = Stream.of(values())
                                                            .collect(Collectors.toMap(Role::getCode, Function.identity()));

    public static Role getRoleByCode(String code) {
        return ROLE_MAP.get(code);
    }
}