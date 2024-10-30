package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.entity.MyManagedGardenEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyManagedGardenJpaRepository extends JpaRepository<MyManagedGardenEntity, Long> {

    @Query(
            """
                    select
                     mmg.myManagedGardenName as myManagedGardenName,
                     mmg.myManagedGardenId as myManagedGardenId,
                     mmg.imageUrl as imageUrl,
                     mmg.createdAt as createdAt,
                     mmg.description as description
                    from MyManagedGardenEntity as mmg
                    where mmg.memberId =:memberId
                    and mmg.myManagedGardenId >:nextManagedGardenId
                    order by mmg.myManagedGardenId
                    """
    )
    List<MyManagedGardensGetRepositoryResponse> getByMemberId(
        @Param("memberId") Long memberId,
        @Param("nextManagedGardenId") Long nextManagedGardenId,
        Pageable pageable);

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
                     mmg.myManagedGardenName as myManagedGardenName,
                     mmg.myManagedGardenId as myManagedGardenId,
                     mmg.imageUrl as imageUrl,
                     mmg.createdAt as createdAt,
                     mmg.description as description
                    from MyManagedGardenEntity as mmg
                    where mmg.myManagedGardenId =:myManagedGardenId
                    """
    )
    MyManagedGardenDetailRepositoryResponse getDetailById(@Param("myManagedGardenId") Long myManagedGardenId);

}
