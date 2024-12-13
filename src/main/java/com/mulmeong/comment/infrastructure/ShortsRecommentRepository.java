package com.mulmeong.comment.infrastructure;

import com.mulmeong.comment.entity.ShortsRecomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortsRecommentRepository extends JpaRepository<ShortsRecomment, Long> {

    Optional<ShortsRecomment> findByRecommentUuid(String memberUuid);

    boolean existsByRecommentUuid(String recommentUuid);
}
