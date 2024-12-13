package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.ShortsRecommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.vo.out.ShortsRecommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/shorts")
public class ShortsRecommentController {
    private final ShortsRecommentService shortsRecommentService;

    @GetMapping("/comments/recomments/{recommentUuid}")
    @Operation(summary = "쇼츠 대댓글 단건 조회", tags = {"Shorts Recomment Service"})
    public BaseResponse<ShortsRecommentResponseVo> getShortsRecomment(@PathVariable String recommentUuid) {
        return new BaseResponse<>(shortsRecommentService.getShortsRecomment(recommentUuid).toVo());
    }

    @GetMapping("/comments/recomments/exist/{recommentUuid}")
    @Operation(tags = {"Feign Service"})
    public boolean existByShortsRecommentUuid(@PathVariable String recommentUuid) {
        return shortsRecommentService.existByRecommentUuid(recommentUuid);
    }

}
