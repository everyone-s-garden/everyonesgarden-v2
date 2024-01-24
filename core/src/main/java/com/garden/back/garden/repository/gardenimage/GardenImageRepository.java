package com.garden.back.garden.repository.gardenimage;

import com.garden.back.garden.domain.GardenImage;

import java.util.List;

public interface GardenImageRepository {
    void deleteByGardenId(Long gardenId);

    GardenImage save(GardenImage gardenImage);

    List<GardenImage> findByGardenId(Long gardenId);

    void delete(GardenImage gardenImage);

    List<String> findGardenImageUrls(Long gardenId);

}
