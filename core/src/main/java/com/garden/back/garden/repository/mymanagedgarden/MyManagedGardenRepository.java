package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyManagedGardenRepository {

    List<MyManagedGardensGetRepositoryResponse> getByMemberId(Long memberId);

    MyManagedGarden getById(Long myManagedGardenId);

    MyManagedGarden save(MyManagedGarden myManagedGarden);

    void delete(Long myManagedGardenId, Long memberId);

    Optional<MyManagedGarden> findById(Long myManagedGardenId);

    MyManagedGardenDetailRepositoryResponse getDetailById(Long myManagedGardenId);

}
