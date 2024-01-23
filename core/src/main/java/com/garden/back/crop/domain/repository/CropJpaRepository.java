package com.garden.back.crop.domain.repository;

import com.garden.back.crop.domain.CropPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropJpaRepository extends JpaRepository<CropPost, Long> {

    Optional<CropPost> findByIdAndCropPostAuthorId(Long id, Long cropPostAuthorId);

    boolean existsByIdAndAndBuyerIdIsNull(Long buyerId);
}
