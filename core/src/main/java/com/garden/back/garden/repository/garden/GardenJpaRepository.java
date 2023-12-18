package com.garden.back.garden.repository.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.garden.dto.GardenByName;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GardenJpaRepository extends JpaRepository<Garden, Long>, GardenCustomRepository, GardenRepository {

    @Query(value = "select " +
            "g.garden_id as gardenId, " +
            "g.address as address, " +
            "g.garden_name as gardenName " +
            "from gardens as g " +
            "where match(g.garden_name) against(:gardenName in boolean mode)",
            countQuery = "select count(g.garden_id) " +
                    "from gardens as g " +
                    "where match(g.garden_name) against(:gardenName in boolean mode) ",
            nativeQuery = true)
    Slice<GardenByName> findGardensByName(@Param("gardenName") String gardenName, Pageable pageable);

    @Query("""
                SELECT
                    g.gardenId as gardenId,
                    g.address as address,
                    g.latitude as latitude,
                    g.longitude as longitude,
                    g.gardenName as gardenName,
                    g.gardenType as gardenType,
                    g.linkForRequest as linkForRequest,
                    g.price as price,
                    g.contact as contact,
                    g.size as size,
                    g.gardenStatus as gardenStatus,
                    g.writerId as writerId,
                    g.recruitStartDate as recruitStartDate,
                    g.recruitEndDate as recruitEndDate,
                    g.useStartDate as useStartDate,
                    g.useEndDate as useEndDate,
                    g.gardenDescription as gardenDescription,
                    gi.imageUrl as imageUrl,
                    CASE WHEN l.garden.gardenId IS NOT NULL THEN true ELSE false END as isLiked,
                    g.isToilet as isToilet,
                    g.isWaterway as isWaterway,
                    g.isEquipment as isEquipment
                FROM
                    Garden g
                LEFT JOIN
                    GardenLike l ON g.gardenId = l.garden.gardenId AND l.memberId = :memberId
                LEFT JOIN
                    GardenImage gi ON g.gardenId = gi.garden.gardenId
            """)
    Slice<GardenGetAll> getAllGardens(Pageable pageable, @Param("memberId") Long memberId);

}
