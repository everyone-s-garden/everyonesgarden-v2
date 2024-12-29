package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.domain.GardenLike;
import com.garden.back.garden.repository.garden.GardenJpaRepository;
import com.garden.back.garden.repository.gardenlike.entity.GardenLikeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GardenLikeRepositoryImpl implements GardenLikeRepository {

    private final GardenLikeJpaRepository gardenLikeJpaRepository;
    private final GardenJpaRepository gardenJpaRepository;

    public GardenLikeRepositoryImpl(GardenLikeJpaRepository gardenLikeJpaRepository, GardenJpaRepository gardenJpaRepository) {
        this.gardenLikeJpaRepository = gardenLikeJpaRepository;
        this.gardenJpaRepository = gardenJpaRepository;
    }

    @Override
    public GardenLike save(GardenLike gardenLike) {
        return gardenLikeJpaRepository.save(GardenLikeEntity.from(gardenLike,
            gardenJpaRepository.getById(gardenLike.getGarden().getGardenId()))).toModel();
    }

    @Override
    public void delete(Long memberId, Long gardenLikeId) {
        gardenLikeJpaRepository.delete(memberId, gardenLikeId);
    }

    @Override
    public List<GardenLike> findAll() {
        return gardenLikeJpaRepository.findAll().stream()
            .map(GardenLikeEntity::toModel)
            .toList();
    }

    @Override
    public Optional<Long> findGardenLikeId(Long memberId, Long gardenId) {
        return gardenLikeJpaRepository.findGardenLikeId(memberId, gardenId);
    }

    @Override
    public boolean isExisted(Long memberId, Long gardenId) {
        return findGardenLikeId(memberId, gardenId).isPresent();
    }

    @Override
    public void delete(Long gardenId) {
        gardenLikeJpaRepository.delete(gardenId);
    }

}
