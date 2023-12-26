package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenGetRepositoryResponse;

import java.util.List;

public interface MyManagedGardenRepository extends MyManagedGardenJpaRepository {

    List<MyManagedGardenGetRepositoryResponse> getByMemberId(Long memberId);

}
