package com.garden.back.garden.repository.gardenimage;

import com.garden.back.garden.repository.gardenimage.entity.GardenImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GardenImageJpaRepository extends JpaRepository<GardenImageEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            "delete from GardenImageEntity as gi where gi.garden.gardenId =:gardenId"
    )
    void deleteByGardenId(@Param("gardenId") Long gardenId);

    @Query(
            "select gi from GardenImageEntity as gi where gi.garden.gardenId =:gardenId"
    )
    List<GardenImageEntity> findByGardenId(@Param("gardenId") Long gardenId);
}
