package com.api.paytree.controller;

import com.api.paytree.dto.CooconDto.*;
import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.service.CooconService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * 연동관리 목록
 * 1. 가상계좌 관리
 */

@RestController
@RequestMapping("/coocon")
public class CooconController {
    @Autowired
    CooconService cooconService;

    // POST : 가상계좌 목록
    public ResponseEntity<CooconList> getCooconList(@RequestBody @Valid Search search) {
        return ResponseEntity.ok(cooconService.getCooconList(search));
    }

    // POST : 가상계좌 등록

    // POST : 가상계좌 상세
    public ResponseEntity<CooconDetail> getCooconDetail(@RequestParam String virtualId) {
        return ResponseEntity.ok(cooconService.getCooconDetail(virtualId));
    }
}