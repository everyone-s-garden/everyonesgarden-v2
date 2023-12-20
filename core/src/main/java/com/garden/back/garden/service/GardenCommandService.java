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

        if (!Objects.equals(gardenToDelete.getWriterId(), param.memberId())) {
            throw new IllegalArgumentException("텃밭 게시글 작성자만 텃밭을 삭제할 수 있습니다.");
        }

        gardenImageRepository.deleteByGardenId(param.gardenId());
        gardenRepository.deleteById(param.gardenId());
    }

    @Transactional
    public void createGardenLike(GardenLikeCreateParam param) {
        Garden gardenToLike = gardenRepository.getById(param.gardenId());
        gardenLikeRepository.save(GardenLike.of(param.memberId(),gardenToLike));
    }

    public void deleteGardenLike(GardenLikeDeleteParam param) {
        gardenLikeRepository.delete(param.memberId(), param.gardenId());
    }

}
