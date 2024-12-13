package com.mulmeong.comment.infrastructure;

import com.mulmeong.comment.entity.FeedRecomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRecommentRepository extends JpaRepository<FeedRecomment, Long> {

    Optional<FeedRecomment> findByRecommentUuid(String recommentUuid);

    boolean existsByRecommentUuid(String recommentUuid);
}
