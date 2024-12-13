package com.mulmeong.comment.application;

import com.mulmeong.comment.dto.in.ShortsRecommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsRecommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsRecommentResponseDto;

public interface ShortsRecommentService {
    ShortsRecommentResponseDto createShortsRecomment(ShortsRecommentRequestDto requestDto);

    void updateShortsRecomment(ShortsRecommentUpdateDto updateDto);

    void deleteShortsRecomment(String memberUuid, String recommentUuid);

    ShortsRecommentResponseDto getShortsRecomment(String recommentUuid);

    boolean existByRecommentUuid(String recommentUuid);
}