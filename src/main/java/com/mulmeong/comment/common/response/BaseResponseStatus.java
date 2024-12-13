package com.mulmeong.comment.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /*
     * 응답 코드와 메시지 표준화하는 ENUM.
     * Http 상태코드, 성공 여부, 응답 메시지, 커스텀 응답 코드, 데이터를 반환.
     */


    /**
     * 200: 요청 성공.
     **/
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

    /**
     * 900: 기타 에러.
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "요청 처리 중 에러가 발생하였습니다."),


    // Comment
    NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, false, 4001, "존재하지 않는 댓글입니다"),
    NO_EXIST_RECOMMENT(HttpStatus.NOT_FOUND, false, 4002, "존재하지 않는 대댓글입니다"),
    NO_DELETE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4003, "댓글 삭제 권한이 없습니다"),
    NO_DELETE_RECOMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4004, "대댓글 삭제 권한이 없습니다"),
    NO_UPDATE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4005, "댓글 수정 권한이 없습니다"),
    NO_UPDATE_RECOMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4006, "대댓글 수정 권한이 없습니다");

    private final HttpStatus httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}