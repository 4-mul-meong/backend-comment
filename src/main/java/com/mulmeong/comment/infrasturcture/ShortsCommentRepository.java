package com.mulmeong.comment.infrasturcture;

import com.mulmeong.comment.entity.ShortsComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortsCommentRepository extends JpaRepository<ShortsComment, Long> {
    Optional<ShortsComment> findByCommentUuid(String commentUuid);

    List<ShortsComment> findByMemberUuid(String memberUuid);

    List<ShortsComment> findByShortsUuid(String shortsUuid);

    List<ShortsComment> findByStatus(Boolean status);

    Boolean existsByCommentUuid(String commentUuid);

    Boolean existsByShortsUuid(String shortsUuid);

}
