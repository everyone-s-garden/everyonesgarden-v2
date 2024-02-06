package com.garden.back.garden.service;

import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.response.GardenDetailRepositoryResponse;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.mymanagedgarden.MyManagedGardenRepository;
import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.request.GardenDetailParam;
import com.garden.back.garden.service.dto.response.*;
import com.garden.back.garden.service.recentview.GardenHistoryManager;
import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.util.PageMaker;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenReadService {
    private final GardenRepository gardenRepository;
    private final MyManagedGardenRepository myManagedGardenRepository;
    private final GardenHistoryManager gardenHistoryManager;
    private final GardenImageRepository gardenImageRepository;

    public GardenReadService(GardenRepository gardenRepository, MyManagedGardenRepository myManagedGardenRepository, GardenHistoryManager gardenHistoryManager, GardenImageRepository gardenImageRepository) {
        this.gardenRepository = gardenRepository;
        this.myManagedGardenRepository = myManagedGardenRepository;
        this.gardenHistoryManager = gardenHistoryManager;
        this.gardenImageRepository = gardenImageRepository;
    }

    public GardenByNameResults getGardensByName(GardenByNameParam gardenByNameParam) {
        return GardenByNameResults.to(gardenRepository.findGardensByName(
            gardenByNameParam.gardenName(),
            PageMaker.makePage(gardenByNameParam.pageNumber())));
    }

    public GardenAllResults getAllGarden(Integer pageNumber) {
        Slice<GardenGetAll> gardens = gardenRepository.getAllGardens(
            PageMaker.makePage(pageNumber));

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
        return RecentGardenResults.to(gardenHistoryManager.findRecentViewGarden(memberId));
    }

    public GardenMineResults getMyGarden(Long memberId) {
        return GardenMineResults.to(gardenRepository.findByWriterId(memberId));
    }

    public GardenLikeByMemberResults getLikeGardensByMember(Long memberId) {
        return GardenLikeByMemberResults.to(
            gardenRepository.getLikeGardenByMember(memberId));
    }

    public MyManagedGardenGetResults getMyManagedGardens(Long memberId) {
        return MyManagedGardenGetResults.to(myManagedGardenRepository.getByMemberId(memberId));
    }

    public MyManagedGardenDetailResult getDetailMyManagedGarden(Long myManagedGardenId) {
        return MyManagedGardenDetailResult.to(
            myManagedGardenRepository.getDetailById(myManagedGardenId));
    }

    public GardenChatRoomInfoResult getGardenChatRoomInfo(Long postId) {
        return GardenChatRoomInfoResult.to(
            gardenRepository.getChatRoomInfo(postId),
            postId);
    }

    public List<String> getGardenImages(Long postId) {
        return gardenImageRepository.findGardenImageUrls(postId);
    }

}
