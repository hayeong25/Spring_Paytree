package com.api.paytree.controller;

import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.SettlementDto.*;
import com.api.paytree.service.SettlementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * 가상계좌 정산 관리
 * 1. 승인 입금 내역
 * 2. 입금 정산 내역
 * 3. 입금 정산 실행
 */

@RestController
@RequestMapping("/settlement")
public class SettlementController {
    @Autowired
    SettlementService settlementService;
    
    // POST : 승인 입금 내역 목록 엑셀 다운로드

    // POST : 입금 정산 내역 목록, 입금 정산 실행 목록
    @PostMapping("/list")
    public ResponseEntity<SettlementList> getSettlementList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(settlementService.getSettlementList(search));
    }

    // POST : 입금 정산 내역 상세, 입금 정산 실행 상세
    @PostMapping("/detail")
    public ResponseEntity<Settlement> getSettlementDetail(@RequestParam String settlementId) {
        return ResponseEntity.ok(settlementService.getSettlementDetail(settlementId));
    }

    // POST : 입금 정산 내역 목록 엑셀 다운로드

    // POST : 입금 정산 실행 목록
    @PostMapping("/performList")
    public ResponseEntity<SettlementList> getSettlementPerformList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(settlementService.getSettlementList(search));
    }
    
    // PUT : 정산 상태 변경
    @PutMapping("/status")
    public ResponseEntity<Settlement> updateSettlementStatus(@RequestBody @Valid ModifyStatus info) {
        return ResponseEntity.ok(settlementService.updateSettlementStatus(info));
    }

    // POST : 입금 정산 실행 목록 엑셀 다운로드
}