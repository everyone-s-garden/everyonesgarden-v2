package com.garden.back.garden;

import com.garden.back.garden.model.PublicDataGarden;

import java.util.List;

public record FarmResponse(
    Wrapper Grid_20171122000000000552_1
) {
    public record Wrapper(List<FarmInfo> row) {}

    public record FarmInfo(
        int ROW_NUM,
        int FARM_ID,
        String FARM_TYPE, //개인, 공동, 지자체, 민간단체 등
        String FARM_NM, // 텃밭 이름
        String AREA_LCD,
        String AREA_LNM,
        String AREA_MCD,
        String AREA_MNM,
        String ADDRESS1, //풀 주소
        String FARM_AREA_INFO, //면적
        String SELL_AREA_INFO, //분양 면적
        String HOMEPAGE, //홈페이지
        String OFF_SITE, // 편의시설
        String REGIST_DT, //등록 날짜
        String UPDT_DT //업데이트 날짜
    ) {
        public PublicDataGarden toEntity() {
            return new PublicDataGarden(
                ROW_NUM,
                FARM_ID,
                FARM_TYPE,
                FARM_NM,
                AREA_LCD,
                AREA_LNM,
                AREA_MCD,
                AREA_MNM,
                ADDRESS1,
                FARM_AREA_INFO,
                SELL_AREA_INFO,
                HOMEPAGE,
                OFF_SITE,
                REGIST_DT,
                UPDT_DT
            );
        }
    }
}
