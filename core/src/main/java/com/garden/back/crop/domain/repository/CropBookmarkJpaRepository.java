package com.garden.back.crop.domain.repository;

import com.garden.back.crop.domain.CropBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropBookmarkJpaRepository extends JpaRepository<CropBookmark, Long> {
    boolean existsByIdAndBookMarkOwnerId(Long bookMarkId, Long bookMarkOwnerId);
    Optional<CropBookmark> findByCropPostIdAndBookMarkOwnerId(Long id, Long bookmarkOwnerId);
}
