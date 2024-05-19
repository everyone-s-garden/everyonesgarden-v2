package com.garden.back.garden.repository.garden;

import com.garden.back.garden.repository.garden.dto.GardenByName;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.response.*;
import com.garden.back.garden.repository.garden.entity.GardenEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GardenJpaRepository extends JpaRepository<GardenEntity, Long> {

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
                g.latitude as latitude,
                g.longitude as longitude,
                g.gardenName as gardenName,
                g.gardenType as gardenType,
                g.price as price,
                g.size as size,
                g.gardenStatus as gardenStatus,
                gi.imageUrl as imageUrl
            FROM
                GardenEntity g
            LEFT JOIN
                GardenImageEntity gi ON g.gardenId = gi.garden.gardenId
        """)
    Slice<GardenGetAll> getAllGardens(Pageable pageable);

    @Query("""
            SELECT
                g.gardenId as gardenId,
                g.address as address,
                g.latitude as latitude,
                g.longitude as longitude,
                g.gardenName as gardenName,
                g.gardenType as gardenType,
                g.price as price,
                g.contact as contact,
                g.size as size,
                g.gardenStatus as gardenStatus,
                g.writerId as writerId,
                g.recruitStartDate as recruitStartDate,
                g.recruitEndDate as recruitEndDate,
                g.gardenDescription as gardenDescription,
                gi.imageUrl as imageUrl,
                CASE
                        WHEN l.gardenLikeId IS NOT NULL THEN l.gardenLikeId
                        ELSE 0
                    END as gardenLikeId,
                g.isToilet as isToilet,
                g.isWaterway as isWaterway,
                g.isEquipment as isEquipment
            FROM
                GardenEntity g
            LEFT JOIN
                GardenLikeEntity l ON g.gardenId = l.garden.gardenId AND l.memberId = :memberId
            LEFT JOIN
                GardenImageEntity gi ON g.gardenId = gi.garden.gardenId
            WHERE g.gardenId =:gardenId
        """
    )
    List<GardenDetailRepositoryResponse> getGardenDetail(
        @Param("memberId") Long memberId,
        @Param("gardenId") Long gardenId
    );

    void deleteById(Long gardenId);

    @Query("""
        select
         g.gardenId as gardenId,
         g.size as size,
         g.gardenName as gardenName,
         g.price as price,
         gi.imageUrl as imageUrl,
         g.gardenStatus as gardenStatus
        from GardenEntity as g
        left join
         GardenImageEntity as gi on g.gardenId = gi.garden.gardenId
        where g.writerId=:writerId
        """)
    List<GardenMineRepositoryResponse> findByWriterId(@Param("writerId") Long writerId);

    @Query(
        """
            select
             g.gardenId as gardenId,
             g.size as size,
             g.gardenName as gardenName,
             g.price as price,
             gi.imageUrl as imageUrl,
             g.gardenStatus as gardenStatus
            from GardenEntity as g
            inner join
             GardenLikeEntity as gl on g.gardenId = gl.garden.gardenId and gl.memberId =:memberId
            left join
             GardenImageEntity as gi on g.gardenId = gi.garden.gardenId
            """
    )
    List<GardenLikeByMemberRepositoryResponse> getLikeGardenByMember(@Param("memberId") Long memberId);

    @Query(
        """
            select
             g.gardenName as gardenName,
             g.gardenStatus as gardenStatus,
             g.price as price,
             gi.imageUrl as imageUrl
            from GardenEntity as g
            left join
             GardenImageEntity as gi on g.gardenId = gi.garden.gardenId
            where g.gardenId =:gardenId
            """
    )
    List<GardenChatRoomInfoRepositoryResponse> getChatRoomInfo(@Param("gardenId") Long gardenId);

    default GardenEntity getByGardenId(Long gardenId) {
        return findById(gardenId).orElseThrow(() -> new EntityNotFoundException("존재하지 텃밭 정보"));
    }

    @Query(
        """
               select
                  g.gardenName as gardenName,
                  g.gardenId as gardenId,
                  g.address as address,
                  g.latitude as latitude,
                  g.longitude as longitude,
                  g.recruitStartDate as recruitStartDate,
                  g.recruitEndDate as recruitEndDate,
                  g.price as price,
                  gi.imageUrl as imageUrl,
                  CASE WHEN l.garden.gardenId IS NOT NULL THEN true ELSE false END as isLiked
               FROM
                  GardenEntity g
               LEFT JOIN
                  GardenLikeEntity l ON g.gardenId = l.garden.gardenId AND l.memberId = :memberId
               LEFT JOIN
                  GardenImageEntity gi ON g.gardenId = gi.garden.gardenId
               ORDER BY g.createAt DESC
               LIMIT 6
            """
    )
    List<RecentCreateGardenRepositoryResponse> getRecentCreatedGardens(@Param("memberId") Long memberId);

    @Query(
           """
                select
                   g.latitude as latitude,
                   g.longitude as longitude
                 from GardenEntity as g
                 where g.gardenId =:gardenId
            """
    )
    Optional<GardenLocationRepositoryResponse> findGardenLocation(@Param("gardenId") Long gardenId);

}
