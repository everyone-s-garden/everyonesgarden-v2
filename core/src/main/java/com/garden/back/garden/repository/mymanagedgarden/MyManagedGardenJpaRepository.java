package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.entity.MyManagedGardenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyManagedGardenJpaRepository extends JpaRepository<MyManagedGardenEntity, Long> {

    @Query(
            """ 
                    select
                     mmg.myManagedGardenId as myManagedGardenId,
                     mmg.imageUrl as imageUrl,
                     mmg.useStartDate as useStartDate,
                     mmg.useEndDate as useEndDate,
                     g.gardenName as gardenName
                    from MyManagedGardenEntity as mmg
                    inner join GardenEntity as g on mmg.gardenId = g.gardenId
                    where mmg.memberId =:memberId
                    """
    )
    List<MyManagedGardensGetRepositoryResponse> getByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(
            """ 
                    delete from MyManagedGardenEntity as mmg where mmg.myManagedGardenId =:myManagedGardenId and mmg.memberId =:memberId
                    """
    )
    void delete(@Param("myManagedGardenId") Long myManagedGardenId, @Param("memberId") Long memberId);

    @Query(
            """ 
                    select
                     mmg.myManagedGardenId as myManagedGardenId,
                     mmg.imageUrl as imageUrl,
                     mmg.useStartDate as useStartDate,
                     mmg.useEndDate as useEndDate,
                     g.gardenName as gardenName,
                     g.address as address
                    from MyManagedGardenEntity as mmg
                    inner join GardenEntity as g on mmg.gardenId = g.gardenId
                    where mmg.myManagedGardenId =:myManagedGardenId
                    """
    )
    MyManagedGardenDetailRepositoryResponse getDetailById(@Param("myManagedGardenId") Long myManagedGardenId);

}
