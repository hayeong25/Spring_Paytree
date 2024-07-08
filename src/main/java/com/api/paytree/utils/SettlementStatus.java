package com.api.paytree.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus {
    WAIT(0), DEFER(1), COMPLETE(2);

    private final int code;
}