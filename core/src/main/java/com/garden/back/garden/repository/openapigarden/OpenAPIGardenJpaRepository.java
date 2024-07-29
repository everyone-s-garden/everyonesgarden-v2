package com.garden.back.garden.repository.openapigarden;

import com.garden.back.garden.domain.OpenAPIGarden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OpenAPIGardenJpaRepository extends JpaRepository<OpenAPIGarden, Long> {

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM OpenAPIGarden o
                WHERE o.uniqueHash = :uniqueHash
            ) THEN true ELSE false END
            """
    )
    Boolean isExisted(@Param("uniqueHash") int uniqueHash);


    @Query(
        """
            SELECT o
            FROM OpenAPIGarden o
            WHERE o.uniqueHash = :uniqueHash
            """
    )
    Optional<OpenAPIGarden> find(@Param("uniqueHash") int uniqueHash);

    @Query(
        """
            SELECT o.openAPIGardenId
            FROM OpenAPIGarden o
            WHERE o.uniqueHash = :uniqueHash
            """
    )
    Optional<String> findOpenAPIGardenId(@Param("uniqueHash") int uniqueHash);
}
