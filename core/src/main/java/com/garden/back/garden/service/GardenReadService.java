package com.garden.back.garden.service;

import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.service.dto.request.GardenGetAllParam;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.GardenByNameResults;
import com.garden.back.garden.util.PageMaker;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GardenReadService {

    private static final int GARDEN_BY_NAME_PAGE_SIZE = 10;
    private final GardenRepository gardenRepository;

    public GardenReadService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    @Transactional(readOnly = true)
    public GardenByNameResults getGardensByName(GardenByNameParam gardenByNameParam) {


        return GardenByNameResults.to(gardenRepository.findGardensByName(
                gardenByNameParam.gardenName(),
                PageMaker.makePage(gardenByNameParam.pageNumber())));
    }

    @Transactional(readOnly = true)
    public GardenAllResults getAllGarden(GardenGetAllParam param) {
        Slice<GardenGetAll> gardens = gardenRepository.getAllGardens(
                PageMaker.makePage(param.pageNumber()),
                param.memberId());

        return GardenAllResults.of(gardens, parseGardenAndImage(gardens));
    }

    private Map<Long, List<String>> parseGardenAndImage(Slice<GardenGetAll> gardensGetAll) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardensGetAll.forEach(gardenGetAll ->
                gardenAndImages
                        .computeIfAbsent(gardenGetAll.getGardenId(), k -> new ArrayList<>())
                        .add(gardenGetAll.getImageUrl())
        );

        return gardenAndImages;
    }

}
