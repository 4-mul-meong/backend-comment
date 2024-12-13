package com.mulmeong.comment.application;

import com.mulmeong.comment.common.exception.BaseException;
import com.mulmeong.comment.common.response.BaseResponseStatus;
import com.mulmeong.comment.dto.in.FeedCommentDeleteDto;
import com.mulmeong.comment.dto.in.FeedCommentRequestDto;
import com.mulmeong.comment.dto.in.FeedCommentUpdateDto;
import com.mulmeong.comment.dto.out.FeedCommentResponseDto;
import com.mulmeong.comment.entity.FeedComment;
import com.mulmeong.comment.infrastructure.FeedCommentRepository;
import com.mulmeong.event.comment.FeedCommentCreateEvent;
import com.mulmeong.event.comment.FeedCommentDeleteEvent;
import com.mulmeong.event.comment.FeedCommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class FeedCommentServiceImpl implements FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public FeedCommentResponseDto createFeedComment(FeedCommentRequestDto requestDto) {
        FeedComment feedComment = feedCommentRepository.save(requestDto.toEntity());
        eventPublisher.send(FeedCommentCreateEvent.toDto(feedComment));
        return FeedCommentResponseDto.toDto(feedComment);
    }

    @Override
    @Transactional
    public void updateFeedComment(FeedCommentUpdateDto updateDto) {
        FeedComment feedComment = feedCommentRepository.findByCommentUuid(updateDto.getCommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT));
        if (!(feedComment.getMemberUuid().equals(updateDto.getMemberUuid()))) {
            throw new BaseException(BaseResponseStatus.NO_UPDATE_COMMENT_AUTHORITY);
        }
        LocalDateTime updatedAt = feedComment.getUpdatedAt();
        feedCommentRepository.save(updateDto.toEntity(feedComment));
        eventPublisher.send(FeedCommentUpdateEvent.toDto(feedComment, updatedAt));
    }

    @Override
    @Transactional
    public void deleteFeedComment(String memberUuid, String commentUuid) {
        FeedComment feedComment = feedCommentRepository.findByCommentUuid(commentUuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT));
        if (!(feedComment.getMemberUuid().equals(memberUuid))) {
            throw new BaseException(BaseResponseStatus.NO_DELETE_COMMENT_AUTHORITY);
        }
        FeedComment deletedComment = feedCommentRepository.save(FeedCommentDeleteDto.toEntity(feedComment));
        eventPublisher.send(FeedCommentDeleteEvent.toDto(deletedComment));
    }

    @Override
    public FeedCommentResponseDto getFeedComment(String commentUuid) {
        FeedComment feedComment = feedCommentRepository.findByCommentUuid(commentUuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT));
        return FeedCommentResponseDto.toDto(feedComment);
    }

    @Override
    public boolean existByCommentUuid(String commentUuid) {
        return feedCommentRepository.existsByCommentUuid(commentUuid);
    }

}
