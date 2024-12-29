package com.garden.back.garden.repository.gardenlike;

import com.garden.back.garden.repository.gardenlike.entity.GardenLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GardenLikeJpaRepository extends JpaRepository<GardenLikeEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query(
            """
            delete from GardenLikeEntity as gl where gl.gardenLikeId =:gardenLikeId and gl.memberId=:memberId
            """)
    void delete(@Param("memberId") Long memberId, @Param("gardenLikeId") Long gardenLikeId);

    @Query(
        """
          select gl.gardenLikeId from GardenLikeEntity as gl where gl.garden.gardenId =:gardenId and gl.memberId=:memberId
        """
    )
    Optional<Long> findGardenLikeId(@Param("memberId") Long memberId, @Param("gardenId") Long gardenId);

    @Modifying(clearAutomatically = true)
    @Query(
        """
        delete from GardenLikeEntity as gl where gl.garden.gardenId =:gardenId
        """)
    void delete(@Param("gardenId") Long gardenLId);

}
