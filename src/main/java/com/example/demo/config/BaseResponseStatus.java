package com.example.demo.config;

import lombok.Getter;
import org.graalvm.compiler.graph.CachedGraph;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    USERS_NOT_ENOUGH_POINT(false, 2011, "금액이 부족합니다."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST] /product
    POST_PRODUCT_EMPTY_DELIVERY(false, 2020, "배달방식을 입력해주세요"),
    POST_PRODUCT_EMPTY_SELLER(false, 2021, "판매자을 입력해주세요"),
    POST_PRODUCT_EMPTY_ITEM(false, 2022, "상품을 입력해주세요"),
    POST_PRODUCT_EMPTY_PRICE(false, 2023, "가격을 입력해주세요"),
    POST_PRODUCT_EMPTY_DISCOUNT(false, 2024, "할인률을 입력해주세요"),
    POST_PRODUCT_EMPTY_MEMBERSHIP_DISCOUNT(false, 2025, "멥버쉽할인율을 입력해주세요"),
    POST_PRODUCT_EMPTY_STOCK(false, 2026, "수량을 입력해주세요"),

    //  [POST] /order
    POST_ORDER_EMPTY_ORDER(false, 2030, "주문을 입력해주세요."),
    POST_ORDER_EMPTY_PRODUCT(false, 2031, "상품을 입력해주세요."),
    POST_ORDER_EMPTY_QUANTITY(false, 2032, "수량을 입력해주세요."),

    //  [POST] /review
    POST_REVIEW_EMPTY_ORDERITEM(false, 2040, "리뷰를 남길 상품인덱스를 입력해주세요."),
    POST_REVIEW_EMPTY_TEXT(false, 2041, "리뷰 내용을 입력해주세요."),
    POST_REVIEW_EMPTY_RATE(false, 2042, "평점을 입력해주세요."),

    //  [POST] /address
    POST_ADDRESS_EMPTY_MAIN_ADDRESS(false, 2050, "기본 주소는 입력해주세요"),

    //  [POST] /inquiry
    POST_INQUIRY_EMPTY_QUESTION(false, 2060, "질문을 입력해주세요."),
    POST_INQUIRY_EMPTY_ANSWER(false, 2061, "답변을 입력해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    RESPONSE_USER_ERROR(false,3001,"해당 유저는 존재하지 않습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    CREATE_FAIL_REVIEW(false,4015," 리뷰 생성 실패"),
    MODIFY_FAIL_REVIEW(false,4016," 리뷰 수정 실패"),
    WITHDRAWAL_FAIL_USER(false, 4017, "회원 탈퇴 실패"),
    CREATE_FAIL_INQUIRY(false, 4018, "문의 생성 실패"),
    CREATE_FAIL_ANSWER(false, 4019, "답변 생성 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    //[PATCH] /order
    PATCH_ORDER_EMPTY_ORDER(false, 4030, "수정할 주문을 입력하세요."),
    PATCH_ORDER_EMPTY_ORDERITEM(false, 4031, "수정할 아이템을 입력하세요."),
    PATCH_ORDER_EMPTY_QUANTITY(false, 4032, "수정할 수량을 입력하세요."),

    //[PATCH] /review
    PATCH_REVIEW_EMPTY_ORDERITEM(false, 4040, "수정할 상품인덱스를 입력해주세요."),
    PATCH_REVIEW_EMPTY_TEXT(false, 4041, "수정할 리뷰 내용을 입력해주세요."),
    PATCH_REVIEW_EMPTY_RATE(false, 4042, "수정할 평점을 입력해주세요."),

    //[PATCH] /address
    PATCH_ADDRESS_EMPTY_MAIN_ADDRESS(false, 4050, "기본 주소는 입력해주세요"),



    // 5000 : 필요시 만들어서 쓰세요
    CANCEL_ORDER_FAIL(false,5014,"주문 취소 실패"),
    CANCEL_MODIFY_FAIL(false,5015,"주문 수정 실패");



    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
