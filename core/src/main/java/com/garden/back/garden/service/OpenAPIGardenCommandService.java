package com.garden.back.garden.service;

import com.garden.back.garden.domain.OpenAPIGarden;
import com.garden.back.garden.repository.openapigarden.OpenAPIGardenRepository;
import com.garden.back.garden.service.dto.request.OpenAPIGardenUpdateParam;
import com.garden.back.garden.service.dto.response.OpenAPIAllGardenResults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpenAPIGardenCommandService {

    private final OpenAPIGardenRepository openAPIGardenRepository;

    public OpenAPIGardenCommandService(OpenAPIGardenRepository openAPIGardenRepository) {
        this.openAPIGardenRepository = openAPIGardenRepository;
    }

    @Transactional
    public void updateOpenAPIGarden(OpenAPIGardenUpdateParam param) {
        param.farminfos().forEach(
            openAPIGarden -> {
                OpenAPIGarden entity = openAPIGarden.toOpenAPIGarden();
                int uniqueHash = entity.getUniqueHash();
                if (Boolean.TRUE.equals(openAPIGardenRepository.isExisted(uniqueHash))) {
                    OpenAPIGarden existedGarden = openAPIGardenRepository.getPublicDataGarden(uniqueHash);
                    existedGarden.updateExitedPublicGarden(entity);
                } else {
                    openAPIGardenRepository.save(entity);
                }
            }
        );
    }

    @Transactional(readOnly = true)
    public OpenAPIAllGardenResults findAll() {
        return OpenAPIAllGardenResults.to(openAPIGardenRepository.findAll());
    }

}
