package com.api.paytree.controller;

import com.api.paytree.dto.AgencyDto.*;
import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.dto.WalletDto.*;
import com.api.paytree.service.AgencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * 영업점 관리
 * 1. 영업점 목록
 * 2. 영업점 지갑 목록
 * 3. 영업점 입출금 내역
 */

@RestController
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    AgencyService agencyService;

    // GET : 페이지 로딩 시 검색 필터 가져오기
    @GetMapping("/filter")
    public ResponseEntity<LoadFilter> getListFilter() {
        return ResponseEntity.ok(agencyService.getListFilter());
    }

    // POST : 영업점 목록
    @PostMapping("/list")
    public ResponseEntity<AgencyList> getAgencyList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(agencyService.getAgencyList(search));
    }

    // POST : 영업점 상세
    @PostMapping("/detail")
    public ResponseEntity<AgencyDetail> getAgencyDetail(@RequestParam String agencyId) {
        return ResponseEntity.ok(agencyService.getAgencyDetail(agencyId));
    }

    // GET : 소속 영업점 조회
    @GetMapping("/belongAgency")
    public ResponseEntity<AgencyList> getBelongAgencyList(@RequestParam String agencyType) {
        return ResponseEntity.ok(agencyService.getBelongAgencyList(agencyType));
    }

    // POST : 영업점 등록
    @PostMapping("/register")
    public ResponseEntity<AgencyDetail> registerAgency(@RequestBody AgencyDetail agencyDetail) {
        return ResponseEntity.ok(agencyService.registerAgency(agencyDetail));
    }

    // PUT : 영업점 수정
    @PutMapping("/detail")
    public ResponseEntity<AgencyDetail> modifyAgency(@RequestBody AgencyDetail agencyDetail) {
        return ResponseEntity.ok(agencyService.modifyAgency(agencyDetail));
    }

    // POST : 영업점 목록 엑셀 다운로드

    // POST : 영업점 지갑 목록
    @PostMapping("/walletList")
    public ResponseEntity<WalletList> getAgencyWalletList(Search search) {
        return ResponseEntity.ok(agencyService.getAgencyWalletList(search));
    }

    // POST : 지갑 송금
    @PostMapping("/send")
    public ResponseEntity<SendWallet> sendToAgencyWallet(@RequestBody @Valid SendWallet sendInfo) {
        return ResponseEntity.ok(agencyService.sendToAgencyWallet(sendInfo));
    }

    // POST : 잔고 회수
    @PostMapping("/return")
    public ResponseEntity<SendWallet> returnToAdminWallet(@RequestBody @Valid SendWallet sendInfo) {
        return ResponseEntity.ok(agencyService.returnToAdminWallet(sendInfo));
    }

    // POST : 영업점 지갑 목록 엑셀 다운로드

    // POST : 영업점 입출금 목록 엑셀 다운로드
}