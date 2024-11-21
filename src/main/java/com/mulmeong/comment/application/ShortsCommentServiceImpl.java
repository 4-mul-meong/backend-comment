package com.mulmeong.comment.application;

import com.mulmeong.comment.common.exception.BaseException;
import com.mulmeong.comment.common.response.BaseResponseStatus;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.ShortsCommentDeleteDto;
import com.mulmeong.comment.dto.in.ShortsCommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsCommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsCommentResponseDto;
import com.mulmeong.comment.entity.ShortsComment;
import com.mulmeong.comment.infrastructure.ShortsCommentRepository;
import com.mulmeong.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortsCommentServiceImpl implements ShortsCommentService {

    private final ShortsCommentRepository shortsCommentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public void createShortsComment(ShortsCommentRequestDto requestDto) {
        ShortsComment shortsComment = shortsCommentRepository.save(requestDto.toEntity());
        eventPublisher.send("shorts-comment-create", ShortsCommentCreateEvent.toDto(shortsComment));
    }

    @Override
    @Transactional
    public void updateShortsComment(ShortsCommentUpdateDto updateDto) {
        ShortsComment shortsComment = shortsCommentRepository.findByCommentUuid(updateDto.getCommentUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT));
        if (!shortsComment.getMemberUuid().equals(updateDto.getMemberUuid())) {
            throw new BaseException(BaseResponseStatus.NO_UPDATE_COMMENT_AUTHORITY);
        }
        LocalDateTime updatedAt = shortsComment.getUpdatedAt();
        shortsCommentRepository.save(updateDto.toEntity(shortsComment));
        eventPublisher.send("shorts-comment-update", ShortsCommentUpdateEvent.toDto(shortsComment, updatedAt));
    }

    @Override
    @Transactional
    public void deleteShortsComment(String memberUuid, String commentUuid) {
        ShortsComment shortsComment = shortsCommentRepository.findByCommentUuid(commentUuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT));
        if (!shortsComment.getMemberUuid().equals(memberUuid)) {
            throw new BaseException(BaseResponseStatus.NO_DELETE_COMMENT_AUTHORITY);
        }
        ShortsComment deletedComment = shortsCommentRepository.save(ShortsCommentDeleteDto.toEntity(shortsComment));
        eventPublisher.send("shorts-comment-delete", ShortsCommentDeleteEvent.toDto(deletedComment));

    }

    @Override
    public ShortsCommentResponseDto getShortsComment(String commentUuid) {
        ShortsComment shortsComment = shortsCommentRepository.findByCommentUuid(commentUuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT)
        );
        if (!shortsComment.isStatus()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT);
        }
        return ShortsCommentResponseDto.toDto(shortsComment);
    }

}
