package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.service.dto.request.*;
import com.garden.back.garden.service.dto.response.*;
import com.garden.back.garden.service.recentview.GardenHistoryManager;
import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.util.PageMaker;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class GardenReadService {
    private final GardenRepository gardenRepository;
    private final GardenImageRepository gardenImageRepository;
    private final GardenHistoryManager gardenHistoryManager;

    public GardenReadService(GardenRepository gardenRepository, GardenImageRepository gardenImageRepository, GardenHistoryManager gardenHistoryManager) {
        this.gardenRepository = gardenRepository;
        this.gardenImageRepository = gardenImageRepository;
        this.gardenHistoryManager = gardenHistoryManager;
    }

    public GardenByNameResults getGardensByName(GardenByNameParam gardenByNameParam) {
        return GardenByNameResults.to(gardenRepository.findGardensByName(
                gardenByNameParam.gardenName(),
                PageMaker.makePage(gardenByNameParam.pageNumber())));
    }

    public GardenAllResults getAllGarden(GardenGetAllParam param) {
        Slice<GardenGetAll> gardens = gardenRepository.getAllGardens(
                PageMaker.makePage(param.pageNumber()),
                param.memberId());

        return GardenAllResults.of(gardens);
    }

    public GardenByComplexesResults getGardensByComplexes(GardenByComplexesParam param) {
        GardensByComplexes gardensByComplexes
                = gardenRepository.getGardensByComplexes(GardenByComplexesParam.to(param));

        return GardenByComplexesResults.of(gardensByComplexes);
    }

    public GardenDetailResult getGardenDetail(GardenDetailParam param) {
        List<GardenDetailRepositoryResponse> gardenDetail = gardenRepository.getGardenDetail(
                param.memberId(),
                param.gardenId());
        GardenDetailResult gardenDetailResult = GardenDetailResult.to(gardenDetail);

        gardenHistoryManager.addRecentViewGarden(
                param.memberId(),
                RecentViewGarden.to(gardenDetailResult));

        return gardenDetailResult;
    }

    public RecentGardenResults getRecentGardens(Long memberId) {
        return RecentGardenResults.to(gardenHistoryManager.getRecentViewGarden(memberId));
    }

    @Transactional
    public void deleteGarden(GardenDeleteParam param) {
        Garden gardenToDelete = gardenRepository.getById(param.gardenId());

        if (!Objects.equals(gardenToDelete.getWriterId(), param.memberId())) {
            throw new IllegalArgumentException("텃밭 게시글 작성자만 텃밭을 삭제할 수 있습니다.");
        }

        gardenImageRepository.deleteByGardenId(param.gardenId());
        gardenRepository.deleteById(param.gardenId());

    }


}
