package com.garden.back.garden.repository.gardenimage;

import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.repository.garden.GardenJpaRepository;
import com.garden.back.garden.repository.gardenimage.entity.GardenImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GardenImageRepositoryImpl implements GardenImageRepository{

    private final GardenImageJpaRepository gardenImageJpaRepository;
    private final GardenJpaRepository gardenJpaRepository;

    public GardenImageRepositoryImpl(GardenImageJpaRepository gardenImageJpaRepository, GardenJpaRepository gardenJpaRepository) {
        this.gardenImageJpaRepository = gardenImageJpaRepository;
        this.gardenJpaRepository = gardenJpaRepository;
    }

    @Override
    public void deleteByGardenId(Long gardenId) {
        gardenImageJpaRepository.deleteByGardenId(gardenId);
    }

    @Override
    public GardenImage save(GardenImage gardenImage) {
        return gardenImageJpaRepository.save(GardenImageEntity.from(gardenImage,
                gardenJpaRepository.getByGardenId(gardenImage.getGarden().getGardenId()))).toModel();
    }

    @Override
    public List<GardenImage> findByGardenId(Long gardenId) {
        return gardenImageJpaRepository.findByGardenId(gardenId).stream()
                .map(GardenImageEntity::toModel)
                .toList();
    }

    @Override
    public void delete(GardenImage gardenImage) {
        gardenImageJpaRepository.delete(GardenImageEntity.from(gardenImage));
    }

    @Override
    public List<String> findGardenImageUrls(Long gardenId) {
        return gardenImageJpaRepository.findGardenImageUrls(gardenId);
    }

}
