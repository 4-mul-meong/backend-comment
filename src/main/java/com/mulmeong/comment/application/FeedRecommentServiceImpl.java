package com.mulmeong.comment.application;

import com.mulmeong.comment.common.exception.BaseException;
import com.mulmeong.comment.common.response.BaseResponseStatus;
import com.mulmeong.comment.dto.in.FeedRecommentRequestDto;
import com.mulmeong.comment.dto.in.FeedRecommentUpdateDto;
import com.mulmeong.comment.dto.out.FeedRecommentResponseDto;
import com.mulmeong.comment.entity.FeedRecomment;
import com.mulmeong.comment.infrastructure.FeedCommentRepository;
import com.mulmeong.comment.infrastructure.FeedRecommentRepository;
import com.mulmeong.event.comment.FeedRecommentCreateEvent;
import com.mulmeong.event.comment.FeedRecommentDeleteEvent;
import com.mulmeong.event.comment.FeedRecommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedRecommentServiceImpl implements FeedRecommentService {

    private final FeedCommentRepository feedCommentRepository;
    private final FeedRecommentRepository feedRecommentRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public FeedRecommentResponseDto createFeedRecomment(FeedRecommentRequestDto requestDto) {
        if (!feedCommentRepository.existsByCommentUuid(requestDto.getCommentUuid())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT);
        }
        FeedRecomment feedRecomment = feedRecommentRepository.save(requestDto.toEntity());
        eventPublisher.send(FeedRecommentCreateEvent.toDto(feedRecomment));
        return FeedRecommentResponseDto.toDto(feedRecomment);
    }

    @Override
    @Transactional
    public void updateFeedRecomment(FeedRecommentUpdateDto updateDto) {
        FeedRecomment feedRecomment = feedRecommentRepository.findByRecommentUuid(updateDto.getRecommentUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        if (!(feedRecomment.getMemberUuid().equals(updateDto.getMemberUuid()))) {
            throw new BaseException(BaseResponseStatus.NO_UPDATE_RECOMMENT_AUTHORITY);
        }
        LocalDateTime updatedAt = feedRecomment.getUpdatedAt();
        feedRecommentRepository.save(updateDto.toEntity(feedRecomment));
        eventPublisher.send(FeedRecommentUpdateEvent.toDto(feedRecomment, updatedAt));
    }

    @Override
    @Transactional
    public void deleteFeedRecomment(String memberUuid, String recommentUuid) {
        FeedRecomment feedRecomment = feedRecommentRepository.findByRecommentUuid(recommentUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        if (!feedRecomment.getMemberUuid().equals(memberUuid)) {
            throw new BaseException(BaseResponseStatus.NO_DELETE_RECOMMENT_AUTHORITY);
        }

        feedRecommentRepository.delete(feedRecomment);
        eventPublisher.send(FeedRecommentDeleteEvent.toDto(recommentUuid, memberUuid));

    }

    @Override
    public FeedRecommentResponseDto getFeedRecomment(String recommentUuid) {
        FeedRecomment feedRecomment = feedRecommentRepository.findByRecommentUuid(recommentUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECOMMENT));
        return FeedRecommentResponseDto.toDto(feedRecomment);
    }

    @Override
    public boolean existByRecommentUuid(String recommentUuid) {
        return feedRecommentRepository.existsByRecommentUuid(recommentUuid);
    }

}
