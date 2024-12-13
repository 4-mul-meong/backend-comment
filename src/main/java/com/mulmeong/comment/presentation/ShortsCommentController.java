package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.ShortsCommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.vo.out.ShortsCommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/shorts")
public class ShortsCommentController {

    private final ShortsCommentService shortsCommentService;

    @GetMapping("/comments/{commentUuid}")
    @Operation(summary = "쇼츠 댓글 단건 조회", tags = {"Shorts Comment Service"})
    public BaseResponse<ShortsCommentResponseVo> getShortsComment(@PathVariable String commentUuid) {

        return new BaseResponse<>(shortsCommentService.getShortsComment(commentUuid).toVo());
    }

    @GetMapping("/comments/exist/{commentUuid}")
    @Operation(tags = {"Feign Service"})
    public boolean existByShortsCommentUuid(@PathVariable String commentUuid) {
        return shortsCommentService.existByCommentUuid(commentUuid);
    }

}
