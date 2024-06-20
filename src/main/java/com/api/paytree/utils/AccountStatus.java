package com.api.paytree.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    WAIT(0), LIVE(1), SUSPEND(2), TERMINATE(3);

    private final int code;
    private static final Map<Integer, AccountStatus> STATUS_MAP = Stream.of(values())
                                                                        .collect(Collectors.toMap(AccountStatus::getCode, Function.identity()));

    public static AccountStatus getAccountStatusByCode(int code) {
        return STATUS_MAP.get(code);
    }
}