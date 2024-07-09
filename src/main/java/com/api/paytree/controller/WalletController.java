package com.api.paytree.controller;

import com.api.paytree.dto.WalletDto.*;
import com.api.paytree.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 지갑 관리
 * 1. 예치금 입금 등록
 * 2. 회원 지급 내역
 */

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;
    
    // POST : 예치금 등록
    @PostMapping("/registerBalance")
    public ResponseEntity<WalletHistory> registerBalance(@RequestBody @Valid WalletHistory info) {
        return ResponseEntity.ok(walletService.registerBalance(info));
    }
    
    // POST : 예치금 입금 목록 엑셀 다운로드

    // POST : 회원 지급 내역 목록 엑셀 다운로드
}