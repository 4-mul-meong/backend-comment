package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.FeedRecommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.FeedRecommentRequestDto;
import com.mulmeong.comment.dto.in.FeedRecommentUpdateDto;
import com.mulmeong.comment.dto.out.FeedRecommentResponseDto;
import com.mulmeong.comment.vo.in.FeedRecommentRequestVo;
import com.mulmeong.comment.vo.in.FeedRecommentUpdateVo;
import com.mulmeong.comment.vo.out.FeedRecommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/feeds")
public class FeedRecommentController {
    private final FeedRecommentService feedRecommentService;

    @GetMapping("/comments/recomments/{recommentUuid}")
    @Operation(summary = "피드 대댓글 단건 조회", tags = {"Feed Recomment Service"})
    public BaseResponse<FeedRecommentResponseVo> getFeedRecomment(@PathVariable String recommentUuid) {
        return new BaseResponse<>(feedRecommentService.getFeedRecomment(recommentUuid).toVo());
    }

    @GetMapping("/comments/recommets/exist/{recommentUuid}")
    @Operation(tags = {"Feign Service"})
    public boolean existByFeedRecommentUuid(@PathVariable String recommentUuid) {
        return feedRecommentService.existByRecommentUuid(recommentUuid);
    }

}
