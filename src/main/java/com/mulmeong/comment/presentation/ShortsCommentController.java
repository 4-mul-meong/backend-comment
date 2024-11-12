package com.mulmeong.comment.presentation;

import com.mulmeong.comment.application.ShortsCommentService;
import com.mulmeong.comment.common.response.BaseResponse;
import com.mulmeong.comment.common.utils.CursorPage;
import com.mulmeong.comment.dto.in.ShortsCommentRequestDto;
import com.mulmeong.comment.dto.in.ShortsCommentUpdateDto;
import com.mulmeong.comment.dto.out.ShortsCommentResponseDto;
import com.mulmeong.comment.vo.in.ShortsCommentRequestVo;
import com.mulmeong.comment.vo.in.ShortsCommentUpdateVo;
import com.mulmeong.comment.vo.out.ShortsCommentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/shorts")
public class ShortsCommentController {

    private final ShortsCommentService shortsCommentService;

    @PostMapping("/{shortsUuid}/comments")
    @Operation(summary = "쇼츠 댓글 추가", tags = {"Shorts Comment Service"})
    public BaseResponse<Void> addShortsComment(
            @RequestBody ShortsCommentRequestVo requestVo,
            @PathVariable String shortsUuid) {
        shortsCommentService.createFeedComment(ShortsCommentRequestDto.toDto(requestVo, shortsUuid));
        return new BaseResponse<>();
    }

    @PutMapping("/comments/{commentUuid}")
    @Operation(summary = "쇼츠 댓글 수정", tags = {"Shorts Comment Service"})
    public BaseResponse<Void> updateShortsComment(
            @RequestHeader("Member-Uuid") String memberUuid,
            @RequestBody ShortsCommentUpdateVo updateVo,
            @PathVariable String commentUuid) {
        shortsCommentService.updateFeedComment(ShortsCommentUpdateDto.toDto(updateVo, commentUuid, memberUuid));
        return new BaseResponse<>();
    }

    @GetMapping("/comments/{commentUuid}")
    @Operation(summary = "쇼츠 댓글 단건 조회", tags = {"Shorts Comment Service"})
    public BaseResponse<ShortsCommentResponseVo> getShortsComment(@PathVariable String commentUuid) {

        return new BaseResponse<>(shortsCommentService.getShortsComment(commentUuid).toVo());
    }

    @DeleteMapping("/comments/{commentUuid}")
    @Operation(summary = "쇼츠 댓글 삭제", tags = {"Shorts Comment Service"})
    public BaseResponse<Void> deleteShortsComment(
            @RequestHeader("Member-Uuid") String memberUuid,
            @PathVariable String commentUuid) {
        shortsCommentService.deleteShortsComment(memberUuid, commentUuid);
        return new BaseResponse<>();
    }

    @GetMapping("/{shortsUuid}/comments")
    @Operation(summary = "쇼츠 댓글 최신순 , 추천순 조회", tags = {"Shorts Comment Service"})
    public BaseResponse<CursorPage<ShortsCommentResponseVo>> getShortsCommentsByPage(
            @PathVariable String shortsUuid,
            @Parameter(
                    description = "정렬 기준",
                    schema = @Schema(allowableValues = {"latest", "likes"})
            )
            @RequestParam(value = "sortBy") String sortBy,
            @RequestParam(value = "nextCursor", required = false) Long lastId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNo", required = false) Integer pageNo
    ) {
        CursorPage<ShortsCommentResponseDto> cursorPage = shortsCommentService.getShortsCommentsByPage(
                shortsUuid, sortBy, lastId, pageSize, pageNo);
        return new BaseResponse<>(
                CursorPage.toCursorPage(cursorPage, cursorPage.getContent().stream()
                        .map(ShortsCommentResponseDto::toVo).toList()));
    }
}
