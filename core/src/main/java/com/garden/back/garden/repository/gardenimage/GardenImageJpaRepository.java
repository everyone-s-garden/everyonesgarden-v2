package com.garden.back.garden.repository.gardenimage;

import com.garden.back.garden.model.GardenImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GardenImageJpaRepository extends JpaRepository<GardenImage, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            "delete from GardenImage as gi where gi.garden.gardenId =:gardenId"
    )
    void deleteByGardenId(@Param("gardenId") Long gardenId);

    @Query(
            "select gi from GardenImage as gi where gi.garden.gardenId =:gardenId"
    )
    Optional<GardenImage> findByGardenId(@Param("gardenId") Long gardenId);
}
