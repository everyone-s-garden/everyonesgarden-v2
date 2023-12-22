package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.GardenLike;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class GardenCommandService {
    private final GardenRepository gardenRepository;
    private final GardenImageRepository gardenImageRepository;
    private final GardenLikeRepository gardenLikeRepository;

    public GardenCommandService(GardenRepository gardenRepository, GardenImageRepository gardenImageRepository, GardenLikeRepository gardenLikeRepository) {
        this.gardenRepository = gardenRepository;
        this.gardenImageRepository = gardenImageRepository;
        this.gardenLikeRepository = gardenLikeRepository;
    }

    @Transactional
    public void deleteGarden(GardenDeleteParam param) {
        Garden gardenToDelete = gardenRepository.getById(param.gardenId());
        gardenToDelete.validWriterId(param.memberId());

        gardenImageRepository.deleteByGardenId(param.gardenId());
        gardenRepository.deleteById(param.gardenId());
    }

    @Transactional
    public Long createGardenLike(GardenLikeCreateParam param) {
        Garden gardenToLike = gardenRepository.getById(param.gardenId());
        GardenLike savedGardenLike = gardenLikeRepository.save(GardenLike.of(param.memberId(), gardenToLike));

        return savedGardenLike.getGardenLikeId();
    }

    public void deleteGardenLike(GardenLikeDeleteParam param) {
        gardenLikeRepository.delete(param.memberId(), param.gardenId());
    }

}
