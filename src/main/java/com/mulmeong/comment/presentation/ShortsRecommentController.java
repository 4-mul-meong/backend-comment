package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.ShortsRecommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.ShortsRecommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsRecommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsRecommentResponseDto;
import com.mulmeong.comment.vo.in.ShortsRecommentRequestVo;
import com.mulmeong.comment.vo.in.ShortsRecommentUpdateVo;
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

    @GetMapping("/comments/{commentUuid}/recomments")
    @Operation(summary = "쇼츠 대댓글 페이지 조회", tags = {"Shorts Recomment Service"})
    public BaseResponse<CursorPage<ShortsRecommentResponseVo>> findShortsRecomment(
            @PathVariable String commentUuid,
            @RequestParam(value = "nextCursor", required = false) Long lastId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNo", required = false) Integer pageNo
    ) {
        CursorPage<ShortsRecommentResponseDto> cursorPage = shortsRecommentService.getShortsRecommets(
                commentUuid, lastId, pageSize, pageNo);

        return new BaseResponse<>(CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                .map(ShortsRecommentResponseDto::toVo).toList()));
    }

}
