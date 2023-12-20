package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class GardenCommandService {
    private final GardenRepository gardenRepository;
    private final GardenImageRepository gardenImageRepository;

    public GardenCommandService(GardenRepository gardenRepository, GardenImageRepository gardenImageRepository) {
        this.gardenRepository = gardenRepository;
        this.gardenImageRepository = gardenImageRepository;
    }

    @Transactional
    public void deleteGarden(GardenDeleteParam param) {
        Garden gardenToDelete = gardenRepository.getById(param.gardenId());

        if (!Objects.equals(gardenToDelete.getWriterId(), param.memberId())) {
            throw new IllegalArgumentException("텃밭 게시글 작성자만 텃밭을 삭제할 수 있습니다.");
        }

        gardenImageRepository.deleteByGardenId(param.gardenId());
        gardenRepository.deleteById(param.gardenId());

    }
}
