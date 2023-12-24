package com.garden.back.garden.repository.gardenimage;

public interface GardenImageRepository extends GardenImageJpaRepository {
    void deleteByGardenId(Long gardenId);
}
