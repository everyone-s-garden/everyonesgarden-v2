package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MyManagedGardenRepository extends MyManagedGardenJpaRepository {

    List<MyManagedGardensGetRepositoryResponse> getByMemberId(Long memberId);

    default MyManagedGarden getById(Long myManagedGardenId) {
        return findById(myManagedGardenId).orElseThrow(() -> new EntityNotFoundException("존재하지 않은 자원입니다."));
    }

}
