package com.garden.back.crop;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.service.request.FindAllCropsPostServiceRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CropQueryService {

    public FindAllCropsPostResponse findAll(FindAllCropsPostServiceRequest request) {
        return new FindAllCropsPostResponse(List.of(new FindAllCropsPostResponse.CropsInfo(1L, "제목", 100000, LocalDate.now(), TradeType.DIRECT_TRADE, true, true, 2)));
    }

    public FindCropsPostDetailsResponse findCropsPostDetails(Long id) {
        return new FindCropsPostDetailsResponse("내용", "글쓴이", "78.2", "서울시 성동구 금호동", CropCategory.FRUIT, 2, List.of("이미지 url"));
    }
}
