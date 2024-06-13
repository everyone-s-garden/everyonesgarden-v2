package com.garden.back.garden.repository.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.repository.garden.dto.GardenByName;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.GardensByComplexesWithScroll;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesWithScrollRepositoryRequest;
import com.garden.back.garden.repository.garden.dto.response.*;
import com.garden.back.garden.repository.garden.entity.GardenEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GardenRepositoryImpl implements GardenRepository {

    private final GardenJpaRepository gardenJpaRepository;
    private final GardenCustomRepository gardenCustomRepository;

    public GardenRepositoryImpl(GardenJpaRepository gardenJpaRepository, GardenCustomRepository gardenCustomRepository) {
        this.gardenJpaRepository = gardenJpaRepository;
        this.gardenCustomRepository = gardenCustomRepository;
    }

    @Override
    public Slice<GardenByName> findGardensByName(String gardenName, Pageable pageable) {
        return gardenJpaRepository.findGardensByName(gardenName, pageable);
    }

    @Override
    public Slice<GardenGetAll> getAllGardens(Pageable pageable) {
        return gardenJpaRepository.getAllGardens(pageable);
    }

    @Override
    public GardensByComplexesWithScroll getGardensByComplexesWithScroll(GardenByComplexesWithScrollRepositoryRequest request) {
        return gardenCustomRepository.getGardensByComplexesWithScroll(request);
    }

    @Override
    public Garden save(Garden garden) {
        return gardenJpaRepository.save(GardenEntity.from(garden)).toModel();
    }

    @Override
    public List<GardenDetailRepositoryResponse> getGardenDetail(Long memberId, Long gardenId) {
        return gardenJpaRepository.getGardenDetail(memberId, gardenId);
    }

    @Override
    public Garden getById(Long gardenId) {
        return gardenJpaRepository.findById(gardenId)
            .orElseThrow(() ->
                new EmptyResultDataAccessException("존재하지 않는 텃밭 분양 정보: " + gardenId, 1))
            .toModel();
    }

    @Override
    public void deleteById(Long gardenId) {
        gardenJpaRepository.deleteById(gardenId);
    }

    @Override
    public GardenMineRepositoryResponses findByWriterId(Long writerId, Long nextGardenId, Pageable pageable) {
        return GardenMineRepositoryResponses.of(
            gardenJpaRepository.findByWriterId(writerId, nextGardenId, pageable),
            pageable);
    }

    @Override
    public List<GardenLikeByMemberRepositoryResponse> getLikeGardenByMember(Long memberId) {
        return gardenJpaRepository.getLikeGardenByMember(memberId);
    }

    @Override
    public List<GardenChatRoomInfoRepositoryResponse> getChatRoomInfo(Long gardenId) {
        return gardenJpaRepository.getChatRoomInfo(gardenId);
    }

    @Override
    public List<RecentCreateGardenRepositoryResponse> getRecentCreatedGardens(Long memberId) {
        return gardenJpaRepository.getRecentCreatedGardens(memberId);
    }

    @Override
    public Optional<GardenLocationRepositoryResponse> findGardenLocation(Long gardenId) {
        return gardenJpaRepository.findGardenLocation(gardenId);
    }

    @Override
    public GardenLocationRepositoryResponse getGardenLocation(Long gardenId) {
        return findGardenLocation(gardenId).orElseThrow(() ->
            new EmptyResultDataAccessException("존재하지 않는 텃밭입니다. gardenId : " + gardenId, 1));
    }

    @Override
    public GardensByComplexes getGardensByComplexes(GardenByComplexesRepositoryRequest request) {
        return gardenCustomRepository.getGardensByComplexes(request);
    }

}
