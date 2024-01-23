package com.garden.back.garden.repository.mymanagedgarden;

import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardenDetailRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryResponse;
import com.garden.back.garden.repository.mymanagedgarden.entity.MyManagedGardenEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MyManagedGardenRepositoryImpl implements MyManagedGardenRepository {

    private final MyManagedGardenJpaRepository myManagedGardenJpaRepository;

    public MyManagedGardenRepositoryImpl(MyManagedGardenJpaRepository myManagedGardenJpaRepository) {
        this.myManagedGardenJpaRepository = myManagedGardenJpaRepository;
    }

    @Override
    public List<MyManagedGardensGetRepositoryResponse> getByMemberId(Long memberId) {
        return myManagedGardenJpaRepository.getByMemberId(memberId);
    }

    @Override
    public MyManagedGarden getById(Long myManagedGardenId) {
        return myManagedGardenJpaRepository.findById(myManagedGardenId)
                .orElseThrow(() -> new EmptyResultDataAccessException("존재하지 않는 가꾸는 텃밭 정보: " + myManagedGardenId, 1))
                .toModel();
    }

    @Override
    public MyManagedGarden save(MyManagedGarden myManagedGarden) {
        return myManagedGardenJpaRepository.save(MyManagedGardenEntity.from(myManagedGarden)).toModel();
    }

    @Override
    public void delete(Long myManagedGardenId, Long memberId) {
        myManagedGardenJpaRepository.delete(myManagedGardenId, memberId);
    }

    @Override
    public Optional<MyManagedGarden> findById(Long myManagedGardenId) {
        return myManagedGardenJpaRepository.findById(myManagedGardenId)
                .map(MyManagedGardenEntity::toModel);
    }

    @Override
    public MyManagedGardenDetailRepositoryResponse getDetailById(Long myManagedGardenId) {
        return myManagedGardenJpaRepository.getDetailById(myManagedGardenId);
    }

}
