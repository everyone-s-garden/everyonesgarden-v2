package com.garden.back.garden.repository.openapigarden;

import com.garden.back.garden.domain.OpenAPIGarden;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpenAPIGardenRepository {

    Boolean isExisted(@Param("uniqueHash") int uniqueHash);

    OpenAPIGarden getPublicDataGarden(@Param("uniqueHash") int uniqueHash);

    OpenAPIGarden save(OpenAPIGarden openAPIGarden);

    List<OpenAPIGarden> findAll();

    Optional<String> findOpenAPIGardenId(int resourceHashId);
}
