package com.api.paytree.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /*
     * C : Client Error
     * D : DB Fail
     * S : Server Error
     */

    NOT_FOUNDED_ACCOUNT(HttpStatus.NOT_FOUND, "C0001", "존재하지 않는 ID입니다."),
    INVALID_ACCOUNT(HttpStatus.NOT_ACCEPTABLE, "C0002", "유효하지 않은 계정입니다."),
    DUPLICATE_ACCOUNT(HttpStatus.NOT_ACCEPTABLE, "C0003", "이미 존재하는 ID입니다."),
    INVALID_PASSWORD(HttpStatus.NOT_ACCEPTABLE, "C0004", "비밀번호가 일치하지 않습니다."),
    NOT_FOUNDED_AGENCY(HttpStatus.NOT_FOUND, "C0011", "존재하지 않는 영업점입니다."),
    INVALID_AGENCY(HttpStatus.NOT_ACCEPTABLE, "C0012", "유효하지 않은 영업점입니다."),
    DUPLICATE_AGENCY(HttpStatus.NOT_ACCEPTABLE, "C0013", "이미 존재하는 영업점입니다."),
    NOT_FOUNDED_DISTRIBUTOR(HttpStatus.NOT_FOUND, "C0021", "존재하지 않는 총판입니다."),
    INVALID_DISTRIBUTOR(HttpStatus.NOT_ACCEPTABLE, "C0022", "유효하지 않은 총판입니다."),
    DUPLICATE_DISTRIBUTOR(HttpStatus.NOT_ACCEPTABLE, "C0023", "이미 존재하는 총판입니다."),
    NOT_FOUNDED_ADMIN(HttpStatus.NOT_FOUND, "C0031", "존재하지 않는 지사입니다."),
    INVALID_ADMIN(HttpStatus.NOT_ACCEPTABLE, "C0022", "유효하지 않은 지사입니다."),
    DUPLICATE_ADMIN(HttpStatus.NOT_ACCEPTABLE, "C0023", "이미 존재하는 지사입니다."),
    INVALID_WALLET(HttpStatus.FORBIDDEN, "C0041", "유효하지 않은 지갑입니다."),
    NOT_ENOUGH_BALANCE(HttpStatus.FORBIDDEN, "C0042", "예치금 잔액이 부족합니다."),

    INVALID_TOKEN(HttpStatus.FORBIDDEN, "C1001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "C1002", "만료된 토큰입니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "C1003", "비정상적인 접근입니다. 로그인 후 이용해주세요."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "C1004", "접근 권한이 없습니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C2001", "요청 파라미터 값을 다시 확인해주세요."),

    SELECT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0001", "데이터 조회 실패"),
    INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0002", "데이터 저장 실패"),
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0003", "데이터 수정 실패"),
    DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0004", "데이터 삭제 실패"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S0001", "서버 오류입니다. 재시도 해주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}