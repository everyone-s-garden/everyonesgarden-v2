package com.garden.back.garden.repository.garden;

import com.garden.back.garden.repository.garden.dto.GardensByComplexes;
import com.garden.back.garden.repository.garden.dto.GardensByComplexesWithScroll;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesWithScrollRepositoryRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GardenCustomRepositoryImpl implements GardenCustomRepository {

    private final EntityManager em;

    public GardenCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public GardensByComplexesWithScroll getGardensByComplexesWithScroll(
            GardenByComplexesWithScrollRepositoryRequest request
    ) {
        String nativeQuery =
               """
                select
                g.garden_id as gardenId,
                g.size as size,
                g.price as price,
                g.garden_status as gardenStatus,
                g.garden_type as gardenType,
                g.garden_name as gardenName,
                g.latitude as latitude,
                g.longitude as longitude,
                gi.image_url as imageUrl
                from gardens as g
                LEFT JOIN
                garden_images as gi ON g.garden_id = gi.garden_id
                where g.garden_type in (%s)
                and MBRContains(ST_LINESTRINGFROMTEXT(%s), g.point)
                """;

        String formattedQuery = String.format(nativeQuery, request.gardenTypes(), request.diagonal());
        formattedQuery = makeScroll(request, formattedQuery);

        Query emNativeQuery = em.createNativeQuery(formattedQuery);

        List<GardensByComplexesWithScroll.GardenByComplexesWithScroll> gardenByComplexes =
                emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                        .addScalar("gardenId", StandardBasicTypes.LONG)
                        .addScalar("size", StandardBasicTypes.STRING)
                        .addScalar("price", StandardBasicTypes.STRING)
                        .addScalar("gardenStatus", StandardBasicTypes.STRING)
                        .addScalar("gardenName", StandardBasicTypes.STRING)
                        .addScalar("latitude", StandardBasicTypes.DOUBLE)
                        .addScalar("longitude", StandardBasicTypes.DOUBLE)
                        .addScalar("imageUrl", StandardBasicTypes.STRING)
                        .addScalar("gardenType",StandardBasicTypes.STRING)
                        .setResultTransformer((tuple, alias) ->
                                new GardensByComplexesWithScroll.GardenByComplexesWithScroll(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (String) tuple[4],
                                        (Double) tuple[5],
                                        (Double) tuple[6],
                                        (String) tuple[7],
                                        (String) tuple[8]
                                ))
                        .list();

        return GardensByComplexesWithScroll.of(gardenByComplexes, isHasNext(gardenByComplexes.size(), request.pageSize()));
    }

    public GardensByComplexes getGardensByComplexes(
        GardenByComplexesRepositoryRequest request
    ) {
        String nativeQuery =
            """
             select
             g.garden_id as gardenId,
             g.size as size,
             g.price as price,
             g.garden_status as gardenStatus,
             g.garden_type as gardenType,
             g.garden_name as gardenName,
             g.latitude as latitude,
             g.longitude as longitude,
             gi.image_url as imageUrl
             from gardens as g
             LEFT JOIN
             garden_images as gi ON g.garden_id = gi.garden_id
             where g.garden_type in (%s)
             and MBRContains(ST_LINESTRINGFROMTEXT(%s), g.point)
             """;

        String formattedQuery = String.format(nativeQuery, request.gardenTypes(), request.diagonal());

        Query emNativeQuery = em.createNativeQuery(formattedQuery);

        List<GardensByComplexes.GardenByComplexes> gardenByComplexes =
            emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("gardenId", StandardBasicTypes.LONG)
                .addScalar("size", StandardBasicTypes.STRING)
                .addScalar("price", StandardBasicTypes.STRING)
                .addScalar("gardenStatus", StandardBasicTypes.STRING)
                .addScalar("gardenName", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("imageUrl", StandardBasicTypes.STRING)
                .addScalar("gardenType",StandardBasicTypes.STRING)
                .setResultTransformer((tuple, alias) ->
                    new GardensByComplexesWithScroll.GardenByComplexesWithScroll(
                        (Long) tuple[0],
                        (String) tuple[1],
                        (String) tuple[2],
                        (String) tuple[3],
                        (String) tuple[4],
                        (Double) tuple[5],
                        (Double) tuple[6],
                        (String) tuple[7],
                        (String) tuple[8]
                    ))
                .list();

        return GardensByComplexes.of(gardenByComplexes);
    }

    private boolean isHasNext(int resultSize, int pageSize) {
        return resultSize == pageSize;
    }

    private String makeScroll(GardenByComplexesWithScrollRepositoryRequest request, String formattedQuery) {
        return String.format("%s LIMIT %d OFFSET %d",
                formattedQuery,
                request.pageSize(),
                request.pageSize() * request.pageNumber());
    }

}
