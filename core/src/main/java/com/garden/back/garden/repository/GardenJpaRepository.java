package com.garden.back.garden.repository;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.dto.GardenByName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GardenJpaRepository extends JpaRepository<Garden, Long> {

    @Query(value = "select g.garden_id as gardenId, g.address as address, g.garden_name as gardenName " +
                    "from gardens as g " +
                    "where match(g.garden_name) against(:gardenName in boolean mode)",
            countQuery = "select count(g.garden_id) " +
                    "from gardens as g " +
                    "where match(g.garden_name) against(:gardenName in boolean mode) ",
            nativeQuery = true)
    Slice<GardenByName> findGardensByName(@Param("gardenName") String gardenName, Pageable pageable);

}
