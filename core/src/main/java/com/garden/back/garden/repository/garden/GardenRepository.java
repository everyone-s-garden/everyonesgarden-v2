package com.garden.back.garden.repository.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.repository.garden.dto.GardenByName;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.garden.repository.garden.dto.response.GardenChatRoomInfoRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.GardenLikeByMemberRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.GardenMineRepositoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface GardenRepository {
    Slice<GardenByName> findGardensByName(String gardenName, Pageable pageable);

    Slice<GardenGetAll> getAllGardens(Pageable pageable);

    GardensByComplexes getGardensByComplexes(GardenByComplexesRepositoryRequest request);

    Garden save(Garden garden);

    List<GardenDetailRepositoryResponse> getGardenDetail(
            Long memberId,
            Long gardenId
    );

    Garden getById(Long gardenId);

    void deleteById(Long gardenId);

    List<GardenMineRepositoryResponse> findByWriterId(Long writerId);

    List<GardenLikeByMemberRepositoryResponse> getLikeGardenByMember(Long memberId);

    List<GardenChatRoomInfoRepositoryResponse> getChatRoomInfo(Long gardenId);

}
