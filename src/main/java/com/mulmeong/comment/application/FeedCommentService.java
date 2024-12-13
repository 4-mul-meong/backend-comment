package com.mulmeong.comment.application;

import com.mulmeong.comment.dto.in.FeedCommentRequestDto;
import com.mulmeong.comment.dto.in.FeedCommentUpdateDto;
import com.mulmeong.comment.dto.out.FeedCommentResponseDto;

public interface FeedCommentService {
    FeedCommentResponseDto createFeedComment(FeedCommentRequestDto requestDto);

    void updateFeedComment(FeedCommentUpdateDto updateDto);

    void deleteFeedComment(String memberUuid, String commentUuid);

    FeedCommentResponseDto getFeedComment(String commentUuid);

    boolean existByCommentUuid(String commentUuid);
}
