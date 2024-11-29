package com.promptoven.authservice.adaptor.web.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

	// 200: 요청 성공
	SUCCESS(HttpStatus.OK, true, "요청에 성공하였습니다."),

	// 400: Bad Request
	BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다."),
	INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, false, "잘못된 파일 형식입니다."),
	FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, false, "파일이 비어있습니다."),
	/*
	 * 401 UNAUTHORIZED: 인증되지 않은 사용자
	 */
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, "인증되지 않은 사용자입니다."),
	/*
	 * 403 FORBIDDEN: 권한이 없는 사용자
	 */
	FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없는 사용자입니다."),
	FAILED_LOGIN(HttpStatus.FORBIDDEN, false, "로그인에 실패했습니다. 아이디나 비밀번호를 다시 확인해 주세요."),

	/*
	 * 404 NOT_FOUND: 리소스를 찾을 수 없음
	 */
	DUPLICATE_EXHIBITION_NAME(HttpStatus.CONFLICT, false, "이미 존재하는 기획전 이름입니다."),
	EXHIBITION_NOT_FOUND(HttpStatus.NOT_FOUND, false, "기획전을 찾을 수 없습니다."),
	INVALID_CSV_FORMAT(HttpStatus.BAD_REQUEST, false, "잘못된 CSV 파일 형식입니다."),
	NOT_FOUND_DATA(HttpStatus.NOT_FOUND, false, "데이터를 찾을 수 없습니다."),

	/*
	 * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
	 */
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, "허용되지 않은 메서드입니다."),

	/*
	 * 409 CONFLICT: 리소스의 현재 상태와 충돌
	 */
	DUPLICATED_DATA(HttpStatus.CONFLICT, false, "중복된 데이터입니다."),

	/*
	 * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "내부 서버 오류입니다."),
	FILE_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "파일 처리 중 오류가 발생했습니다."),
	;

	private final HttpStatusCode httpStatusCode;
	private final boolean isSuccess;
	private final String message;
}