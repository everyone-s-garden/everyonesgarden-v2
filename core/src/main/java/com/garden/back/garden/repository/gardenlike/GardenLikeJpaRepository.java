package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.repository.gardenlike.entity.GardenLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GardenLikeJpaRepository extends JpaRepository<GardenLikeEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            """
            delete from GardenLikeEntity as gl where gl.garden.gardenId =:gardenId and gl.memberId=:memberId
            """)
    void delete(@Param("memberId") Long memberId, @Param("gardenId") Long gardenId);

}
