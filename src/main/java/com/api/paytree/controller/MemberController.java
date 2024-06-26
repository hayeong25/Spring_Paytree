package com.api.paytree.controller;

import com.api.paytree.dto.MemberDto.*;
import com.api.paytree.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * 회원 관리
 * 1. 회원 목록
 * 2. 회원 지갑 목록
 * 3. 회원 입출금 내역
 */

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    // POST : 회원 목록
    @PostMapping("/list")
    public ResponseEntity<MemberList> getMemberList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(memberService.getMemberList(search));
    }

    // POST : 회원 상세
    @PostMapping("/detail")
    public ResponseEntity<MemberDetail> getMemberDetail(@RequestParam String memberId) {
        return ResponseEntity.ok(memberService.getMemberDetail(memberId));
    }

    // PUT : 회원 정보 수정
    @PutMapping("/detail")
    public ResponseEntity<MemberDetail> modifyMember(@RequestBody @Valid Member member) {
        return ResponseEntity.ok(memberService.modifyMember(member));
    }

    // POST : 회원 목록 엑셀 다운로드

    // POST : 회원 지갑 목록
    @PostMapping("/walletList")
    public ResponseEntity<WalletList> getMemberWalletList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(memberService.getMemberWalletList(search));
    }

    // POST : 지갑 송금
    @PostMapping("/send")
    public ResponseEntity<SendWallet> sendToMemberWallet(@RequestBody @Valid SendWallet sendInfo) {
        return ResponseEntity.ok(memberService.sendToMemberWallet(sendInfo));
    }

    // POST : 잔고 회수
    @PostMapping("/return")
    public ResponseEntity<SendWallet> returnToAgencyWallet(@RequestBody @Valid SendWallet sendInfo) {
        return ResponseEntity.ok(memberService.returnToAgencyWallet(sendInfo));
    }

    // POST : 회원 지갑 목록 엑셀 다운로드
}