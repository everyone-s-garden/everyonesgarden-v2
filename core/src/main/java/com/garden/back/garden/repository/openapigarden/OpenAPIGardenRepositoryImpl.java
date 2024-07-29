package com.garden.back.garden.repository.openapigarden;

import com.garden.back.garden.domain.OpenAPIGarden;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class OpenAPIGardenRepositoryImpl implements OpenAPIGardenRepository {

    private final OpenAPIGardenJpaRepository openAPIGardenJpaRepository;

    public OpenAPIGardenRepositoryImpl(OpenAPIGardenJpaRepository openAPIGardenJpaRepository) {
        this.openAPIGardenJpaRepository = openAPIGardenJpaRepository;
    }

    @Override
    public Boolean isExisted(int uniqueHash) {
        return openAPIGardenJpaRepository.isExisted(uniqueHash);
    }

    @Override
    public OpenAPIGarden getPublicDataGarden(int uniqueHash) {
        return openAPIGardenJpaRepository.find(uniqueHash).orElseThrow(
            () -> new NoSuchElementException("해당하는 텃밭 분양 공공데이터는 존재하지 않습니다.")
        );
    }

    @Override
    public OpenAPIGarden save(OpenAPIGarden openAPIGarden) {
        return openAPIGardenJpaRepository.save(openAPIGarden);
    }

    @Override
    public List<OpenAPIGarden> findAll() {
        return openAPIGardenJpaRepository.findAll();
    }

    @Override
    public Optional<String> findOpenAPIGardenId(int resourceHashId) {
        return openAPIGardenJpaRepository.findOpenAPIGardenId(resourceHashId);
    }

}
