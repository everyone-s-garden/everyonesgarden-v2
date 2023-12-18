package com.garden.back.garden.service;

import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import com.garden.back.garden.service.dto.request.GardenGetAllParam;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.garden.service.dto.response.GardenByNameResults;
import com.garden.back.garden.util.GeometryUtil;
import com.garden.back.garden.util.PageMaker;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class GardenReadService {
    private final GardenRepository gardenRepository;

    public GardenReadService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
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

}
