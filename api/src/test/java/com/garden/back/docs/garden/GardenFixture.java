package com.garden.back.docs.garden;

import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.GardenByNameResults;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class GardenFixture {
    public static GardenByNameParam gardenByNameParam() {
        return new GardenByNameParam("도연",0);
    }
    public static GardenByNameResults gardenByNameResults() {
        List<GardenByNameResults.GardenByNameResult> gardensByName = List.of(
                new GardenByNameResults.GardenByNameResult(1L, "도연이네 텃밭", "제주도 서귀포"),
                new GardenByNameResults.GardenByNameResult(2L, "도연이네 주말농장", "경기도 수정시 분당구")
        );

        return new GardenByNameResults(gardensByName,false);
    }
}
