package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.domain.GardenLike;

import java.util.List;
import java.util.Optional;

public interface GardenLikeRepository {
    GardenLike save(GardenLike gardenLike);

    void delete(Long memberId, Long gardenId);

    List<GardenLike> findAll();

    Optional<Long> findGardenLikeId(Long memberId, Long gardenId);

    boolean isExisted(Long memberId, Long gardenId);

}
