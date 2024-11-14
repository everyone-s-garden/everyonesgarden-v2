package com.garden.back.garden.service;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.domain.GardenLike;
import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.domain.dto.MyManagedGardenCreateDomainRequest;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.gardenimage.GardenImageRepository;
import com.garden.back.garden.repository.gardenlike.GardenLikeRepository;
import com.garden.back.garden.repository.mymanagedgarden.MyManagedGardenRepository;
import com.garden.back.garden.service.dto.request.*;
import com.garden.back.global.image.ImageUploader;
import com.garden.back.global.image.ParallelImageUploader;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GardenCommandService {
    private static final String GARDEN_IMAGE_DIRECTORY = "garden/";
    private final GardenRepository gardenRepository;
    private final GardenImageRepository gardenImageRepository;
    private final GardenLikeRepository gardenLikeRepository;
    private final MyManagedGardenRepository myManagedGardenRepository;
    private final ParallelImageUploader parallelImageUploader;
    private final ImageUploader imageUploader;

    public GardenCommandService(
        GardenRepository gardenRepository,
        GardenImageRepository gardenImageRepository,
        GardenLikeRepository gardenLikeRepository,
        MyManagedGardenRepository myManagedGardenRepository,
        ParallelImageUploader parallelImageUploader, ImageUploader imageUploader) {
        this.gardenRepository = gardenRepository;
        this.gardenImageRepository = gardenImageRepository;
        this.gardenLikeRepository = gardenLikeRepository;
        this.myManagedGardenRepository = myManagedGardenRepository;
        this.parallelImageUploader = parallelImageUploader;
        this.imageUploader = imageUploader;
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
        if (gardenLikeRepository.isExisted(param.memberId(), param.gardenId())) {
            throw new DuplicateRequestException("이미 등록된 좋아요 합니다.");
        }

        Garden gardenToLike = gardenRepository.getById(param.gardenId());
        GardenLike savedGardenLike = gardenLikeRepository.save(GardenLike.of(param.memberId(), gardenToLike));

        return savedGardenLike.getGardenLikeId();
    }

    @Transactional
    public void deleteGardenLike(GardenLikeDeleteParam param) {
        gardenLikeRepository.delete(param.memberId(), param.gardenLikeId());
    }

    @Transactional
    public Long createGarden(GardenCreateParam param) {
        Garden savedGarden = gardenRepository.save(param.toEntity());

        List<String> uploadImageUrls = parallelImageUploader.upload(GARDEN_IMAGE_DIRECTORY, param.gardenImages());
        uploadImageUrls.forEach(uploadImageUrl -> gardenImageRepository.save(GardenImage.of(uploadImageUrl, savedGarden)));

        return savedGarden.getGardenId();
    }

    @Transactional
    public Long updateGarden(GardenUpdateParam param) {
        Garden gardenToUpdate = gardenRepository.getById(param.gardenId());
        gardenToUpdate.updateGarden(param.toGardenUpdateDomainRequest());
        gardenRepository.save(gardenToUpdate);

        deleteGardenImages(param.gardenId(), param.remainGardenImageUrls());
        saveNewGardenImages(gardenToUpdate, param.newGardenImages());

        return gardenToUpdate.getGardenId();
    }

    private void deleteGardenImages(Long gardenId, List<String> remainGardenImageUrls) {
        List<GardenImage> gardenImages = gardenImageRepository.findByGardenId(gardenId);
        gardenImages.stream()
            .filter(gardenImage -> !remainGardenImageUrls.contains(gardenImage.getImageUrl()))
            .forEach(gardenImageRepository::delete);
    }

    private void saveNewGardenImages(Garden garden, List<MultipartFile> newImage) {
        if (!newImage.isEmpty()) {
            List<String> uploadImageUrls = parallelImageUploader.upload(GARDEN_IMAGE_DIRECTORY, newImage);
            uploadImageUrls.forEach(uploadImageUrl -> gardenImageRepository.save(GardenImage.of(uploadImageUrl, garden)));
        }
    }

    @Transactional
    public void deleteMyManagedGarden(MyManagedGardenDeleteParam param) {
        myManagedGardenRepository.delete(param.myManagedGardenId(), param.memberId());
    }

    @Transactional
    public Long createMyManagedGarden(MyManagedGardenCreateParam param) {
        String uploadImageUrls = uploadGardenImage(param.myManagedGardenImage());
        MyManagedGardenCreateDomainRequest myManagedGardenCreateDomainRequest = param.to(uploadImageUrls);
        MyManagedGarden savedMyManagedGarden = myManagedGardenRepository.save(
            MyManagedGarden.to(myManagedGardenCreateDomainRequest));

        return savedMyManagedGarden.getMyManagedGardenId();
    }

    @Transactional
    public Long updateMyManagedGarden(MyManagedGardenUpdateParam param) {
        MyManagedGarden myManagedGarden = myManagedGardenRepository.getById(param.myManagedGardenId());
        String uploadImageUrls = myManagedGarden.getImageUrl();

        if (param.myManagedGardenImage().isPresent()) {
            deleteGardenImage(myManagedGarden.getImageUrl());
            uploadImageUrls = uploadGardenImage(param.myManagedGardenImage().get());
        }

        myManagedGarden.update(param.toMyManagedGardenUpdateDomainRequest(uploadImageUrls));
        myManagedGardenRepository.save(myManagedGarden);
        return myManagedGarden.getMyManagedGardenId();
    }

    private String uploadGardenImage(MultipartFile gardenImage) {
        return imageUploader.upload(GARDEN_IMAGE_DIRECTORY, gardenImage);
    }

    private void deleteGardenImage(String imageUrl) {
        imageUploader.delete(GARDEN_IMAGE_DIRECTORY, imageUrl);
    }

    @Transactional
    public void updateGardenFromOpenAPI(GardenUpdateFromOpenAPIParams params) {
        params.params().forEach(
            param -> {
                int resourceHashId = param.resourceHashId();
                if (Boolean.TRUE.equals(gardenRepository.isExisted(resourceHashId))) {
                    Garden gardenToUpdate = gardenRepository.getGardenEntity(resourceHashId);
                    gardenToUpdate.updateGarden(param.toGardenUpdateDomainRequest());
                    gardenRepository.save(gardenToUpdate);
                } else {
                    gardenRepository.save(param.toEntity());
                }
            }
        );
    }

}
