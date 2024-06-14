package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryParam;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponses;

import java.util.Optional;

public interface MyManagedGardenRepository {

    MyManagedGardensGetRepositoryResponses getByMemberId(MyManagedGardensGetRepositoryParam param);

    MyManagedGarden getById(Long myManagedGardenId);

    MyManagedGarden save(MyManagedGarden myManagedGarden);

    void delete(Long myManagedGardenId, Long memberId);

    Optional<MyManagedGarden> findById(Long myManagedGardenId);

    MyManagedGardenDetailRepositoryResponse getDetailById(Long myManagedGardenId);

}
