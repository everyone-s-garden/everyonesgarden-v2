package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.model.GardenLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenLikeJpaRepository extends JpaRepository<GardenLike, Long> {
}
