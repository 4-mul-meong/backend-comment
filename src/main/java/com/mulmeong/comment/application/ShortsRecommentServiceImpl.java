package com.mulmeong.comment.application;

import com.mulmeong.comment.common.exception.BaseException;
import com.mulmeong.comment.common.response.BaseResponseStatus;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.ShortsRecommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsRecommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsRecommentResponseDto;
import com.mulmeong.comment.entity.ShortsRecomment;
import com.mulmeong.comment.infrastructure.ShortsCommentRepository;
import com.mulmeong.comment.infrastructure.ShortsRecommentRepository;
import com.mulmeong.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortsRecommentServiceImpl implements ShortsRecommentService {

    private final ShortsCommentRepository shortsCommentRepository;
    private final ShortsRecommentRepository shortsRecommentRepository;
    private final EventPublisher eventPublisher;

    @Override
    public void createShortsRecomment(ShortsRecommentRequestDto requestDto) {
        if (!shortsCommentRepository.existsByCommentUuid(requestDto.getCommentUuid())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT);
        }
        ShortsRecomment shortsRecomment = shortsRecommentRepository.save(requestDto.toEntity());
        eventPublisher.send("shorts-recomment-create", ShortsRecommentCreateEvent.toDto(shortsRecomment));
    }

    @Override
    @Transactional
    public void updateShortsRecomment(ShortsRecommentUpdateDto updateDto) {
        ShortsRecomment shortsRecomment = shortsRecommentRepository.findByRecommentUuid(updateDto.getRecommentUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        if (!shortsRecomment.getMemberUuid().equals(updateDto.getMemberUuid())) {
            throw new BaseException(BaseResponseStatus.NO_UPDATE_RECOMMENT_AUTHORITY);
        }
        LocalDateTime updatedAt = shortsRecomment.getUpdatedAt();
        shortsRecommentRepository.save(updateDto.toEntity(shortsRecomment));
        eventPublisher.send("shorts-recomment-update", ShortsRecommentUpdateEvent.toDto(shortsRecomment, updatedAt));
    }

    @Override
    @Transactional
    public void deleteShortsRecomment(String memberUuid, String recommentUuid) {
        ShortsRecomment shortsRecomment = shortsRecommentRepository.findByRecommentUuid(recommentUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        if (!shortsRecomment.getMemberUuid().equals(memberUuid)) {
            throw new BaseException(BaseResponseStatus.NO_UPDATE_RECOMMENT_AUTHORITY);
        }

        shortsRecommentRepository.delete(shortsRecomment);
        eventPublisher.send("shorts-recomment-delete", ShortsRecommentDeleteEvent.toDto(recommentUuid));
    }

    @Override
    public ShortsRecommentResponseDto getShortsRecomment(String recommentUuid) {
        ShortsRecomment shortsRecomment = shortsRecommentRepository.findByRecommentUuid(recommentUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        return ShortsRecommentResponseDto.toDto(shortsRecomment);
    }

}
