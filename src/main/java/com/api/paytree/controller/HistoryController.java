package com.api.paytree.controller;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.WalletDto.*;
import com.api.paytree.service.HistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    // POST : 영업점 & 회원 입출금 내역, 승인 입금 내역, 예치금 입금 등록 목록, 회원 지급 내역
    @PostMapping("/list")
    public ResponseEntity<HistoryList> getWalletHistoryList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(historyService.getWalletHistoryList(search));
    }

    // POST : 영업점 & 회원 입출금 내역 상세, 승인 입금 내역 상세, 예치금 입금 등록 상세, 회원 지급 내역 상세
    @PostMapping("/detail")
    public ResponseEntity<WalletHistory> getWalletHistoryDetail(@RequestParam String historyNo) {
        return ResponseEntity.ok(historyService.getWalletHistoryDetail(historyNo));
    }
}