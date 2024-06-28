package com.api.paytree.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VirtualAccount {
    COOCON("2");

    private final String code;
}