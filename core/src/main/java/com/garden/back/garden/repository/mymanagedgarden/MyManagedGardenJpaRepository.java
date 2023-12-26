package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyManagedGardenJpaRepository extends JpaRepository<MyManagedGarden, Long> {

    @Query(
            """ 
                    select
                     mmg.myManagedGardenId as myManagedGardenId,
                     mmg.imageUrl as imageUrl,
                     mmg.useStartDate as useStartDate,
                     mmg.useEndDate as useEndDate,
                     g.gardenName as gardenName
                    from MyManagedGarden as mmg
                    inner join Garden as g on mmg.gardenId = g.gardenId
                    where mmg.memberId =:memberId
                    """
    )
    List<MyManagedGardensGetRepositoryResponse> getByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(
            """ 
                    delete from MyManagedGarden as mmg where mmg.myManagedGardenId =:myManagedGardenId and mmg.memberId =:memberId
                    """
    )
    void delete(@Param("myManagedGardenId") Long myManagedGardenId, @Param("memberId") Long memberId);
}
