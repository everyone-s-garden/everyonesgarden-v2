package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.GardenImage;
import com.garden.back.garden.model.GardenLike;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.service.dto.request.GardenCreateParam;
import com.garden.back.garden.service.dto.request.GardenDeleteParam;
import com.garden.back.garden.service.dto.request.GardenLikeCreateParam;
import com.garden.back.garden.service.dto.request.GardenLikeDeleteParam;
import com.garden.back.global.image.ParallelImageUploader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GardenCommandService {
    private static final String GARDEN_IMAGE_DIRECTORY = "garden/";
    private final GardenRepository gardenRepository;
    private final GardenImageRepository gardenImageRepository;
    private final GardenLikeRepository gardenLikeRepository;
    private final ParallelImageUploader parallelImageUploader;

    public GardenCommandService(GardenRepository gardenRepository, GardenImageRepository gardenImageRepository, GardenLikeRepository gardenLikeRepository, ParallelImageUploader parallelImageUploader) {
        this.gardenRepository = gardenRepository;
        this.gardenImageRepository = gardenImageRepository;
        this.gardenLikeRepository = gardenLikeRepository;
        this.parallelImageUploader = parallelImageUploader;
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

    public Long createGarden(GardenCreateParam param) {
        Garden savedGarden = gardenRepository.save(GardenCreateParam.toEntity(param));

        List<String> uploadImageUrls = parallelImageUploader.upload(GARDEN_IMAGE_DIRECTORY, param.gardenImages());
        uploadImageUrls.forEach(uploadImageUrl -> gardenImageRepository.save(GardenImage.of(uploadImageUrl, savedGarden)));

        return savedGarden.getGardenId();
    }

}
