package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.global.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record GardenCreateParam(
    List<MultipartFile> gardenImages,
    String gardenName,
    String price,
    String size,
    GardenStatus gardenStatus,
    String contact,
    String address,
    Double latitude,
    Double longitude,
    GardenFacility gardenFacility,
    String gardenDescription,
    LocalDate recruitStartDate,
    LocalDate recruitEndDate,
    Long writerId

) {
    public Garden toEntity() {
        return Garden.createGarden(
            address,
            latitude,
            longitude,
            GeometryUtil.createPoint(latitude, longitude),
            GardenType.PRIVATE,
            gardenName,
            gardenStatus,
            price,
            contact,
            size,
            gardenDescription,
            recruitStartDate.toString(),
            recruitEndDate.toString(),
            gardenFacility.getGardenFacilities(),
            writerId
        );
    }
    public record GardenFacility(
        Boolean isToilet,
        Boolean isWaterway,
        Boolean isEquipment
    ) {

        private String getGardenFacilities() {
            StringBuilder sb = new StringBuilder();
            if (Boolean.TRUE.equals(isToilet)) {
                sb.append("화장실");
            }
            if (Boolean.TRUE.equals(isEquipment)) {
                if (!sb.isEmpty()) sb.append(", ");
                sb.append("농기구");
            }
            if (Boolean.TRUE.equals(isWaterway)) {
                if (!sb.isEmpty()) sb.append(", ");
                sb.append("수로");
            }

            return sb.toString();
        }
    }

}
