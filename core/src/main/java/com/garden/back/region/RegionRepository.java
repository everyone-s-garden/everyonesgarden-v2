package com.garden.back.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "SELECT * FROM regions WHERE MATCH(regions.full_address) AGAINST(:address IN NATURAL LANGUAGE MODE ) LIMIT :pageSize OFFSET :pageNumber", nativeQuery = true)
    List<Region> findAllRegions(@Param("address") String address, @Param("pageSize") int limit, @Param("pageNumber") int offset);
}
