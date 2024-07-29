package com.garden.back.garden;

import com.garden.back.garden.service.dto.request.OpenAPIGardenUpdateParam;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public record FarmResponse(
    FarmResult Grid_20171122000000000552_1
) {
   public record FarmResult(
       int totalCnt,
       int startRow,
       int endRow,
       Result result,
       List<FarmInfo> row
   ){
   }
    public OpenAPIGardenUpdateParam toOpenAPIGardenUpdateParam() {
        return new OpenAPIGardenUpdateParam(
            Grid_20171122000000000552_1.row
                .stream()
                .filter(this::validateFarmInfo)
                .map(FarmInfo::toFarmInfo).toList());
    }

    public record Result(
        String code,
        String message
    ) {

    }

    public record FarmInfo(
        String ROW_NUM,
        String FARM_ID,
        String FARM_TYPE,
        String FARM_NM,
        String AREA_LCD,
        String AREA_LNM,
        String AREA_MCD,
        String AREA_MNM,
        String ADDRESS1,
        String FARM_AREA_INFO,
        String SELL_AREA_INFO,
        String HOMEPAGE,
        String COLLEC_PROD,
        String OFF_SITE,
        String APPLY_MTHD,
        String PRICE,
        String POSLAT,
        String POSLNG,
        String REGIST_DT,
        String UPDT_DT
    ) {

        public OpenAPIGardenUpdateParam.FarmInfo toFarmInfo() {
            return new OpenAPIGardenUpdateParam.FarmInfo(
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
                COLLEC_PROD,
                OFF_SITE,
                APPLY_MTHD,
                PRICE,
                POSLAT,
                POSLNG,
                REGIST_DT,
                UPDT_DT
            );
        }

    }

    private boolean validateFarmInfo(FarmInfo farmInfo) {
        if (isInvalidCoordinates(farmInfo.POSLAT, farmInfo.POSLNG)) {
            return false;
        }

        Double latitude = parseCoordinate(farmInfo.POSLAT);
        Double longitude = parseCoordinate(farmInfo.POSLNG);

        if (latitude == null || longitude == null) {
            return false;
        }

        if (!isValidLatitude(latitude) || !isValidLongitude(longitude)) {
            return false;
        }

        return !StringUtils.isBlank(farmInfo.FARM_NM);
    }

    private boolean isInvalidCoordinates(String latitude, String longitude) {
        return StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude);
    }

    private Double parseCoordinate(String coordinate) {
        try {
            return Double.parseDouble(coordinate);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isValidLatitude(double latitude) {
        return latitude >= 33 && latitude <= 43;
    }

    private boolean isValidLongitude(double longitude) {
        return longitude >= 124 && longitude <= 132;
    }

}
