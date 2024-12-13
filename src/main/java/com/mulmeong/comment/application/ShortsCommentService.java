package com.mulmeong.comment.application;

import com.mulmeong.comment.dto.in.ShortsCommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsCommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsCommentResponseDto;

public interface ShortsCommentService {
    ShortsCommentResponseDto createShortsComment(ShortsCommentRequestDto requestDto);

    void updateShortsComment(ShortsCommentUpdateDto updateDto);

    void deleteShortsComment(String memberUuid, String commentUuid);

    ShortsCommentResponseDto getShortsComment(String commentUuid);

    boolean existByCommentUuid(String commentUuid);

}
