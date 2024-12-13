package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.FeedCommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.FeedCommentRequestDto;
import com.mulmeong.comment.dto.in.FeedCommentUpdateDto;
import com.mulmeong.comment.dto.out.FeedCommentResponseDto;
import com.mulmeong.comment.vo.in.FeedCommentRequestVo;
import com.mulmeong.comment.vo.in.FeedCommentUpdateVo;
import com.mulmeong.comment.vo.out.FeedCommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/feeds")
public class FeedCommentController {

    private final FeedCommentService feedCommentService;

    @GetMapping("/comments/{commentUuid}")
    @Operation(summary = "피드 댓글 단건 조회", tags = {"Feed Comment Service"})
    public BaseResponse<FeedCommentResponseVo> getFeedComment(@PathVariable String commentUuid) {

        return new BaseResponse<>(feedCommentService.getFeedComment(commentUuid).toVo());
    }

    @GetMapping("/comments/exist/{commentUuid}")
    @Operation(tags = {"Feign Service"})
    public boolean existByFeedCommentUuid(@PathVariable String commentUuid) {
        return feedCommentService.existByCommentUuid(commentUuid);
    }
}
