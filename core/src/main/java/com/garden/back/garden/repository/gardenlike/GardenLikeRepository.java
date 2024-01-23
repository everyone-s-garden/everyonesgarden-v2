package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.domain.GardenLike;

import java.util.List;

public interface GardenLikeRepository {
    GardenLike save(GardenLike gardenLike);

    void delete(Long memberId, Long gardenId);

    List<GardenLike> findAll();

}
