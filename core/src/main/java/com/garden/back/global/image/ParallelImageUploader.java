package com.garden.back.global.image;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ParallelImageUploader {

    private final ImageUploader imageUploader;
    private final Executor imageExecutor;

    public ParallelImageUploader(ImageUploader imageUploader, Executor imageExecutor) {
        this.imageUploader = imageUploader;
        this.imageExecutor = imageExecutor;
    }

    public List<String> upload(String directory, List<MultipartFile> multipartFiles) {
        if (multipartFiles.isEmpty()) {
            return Collections.emptyList();
        }
        List<CompletableFuture<String>> futures = multipartFiles.stream()
            .map(multipartFile ->
                CompletableFuture.supplyAsync(
                    () -> imageUploader.upload(directory, multipartFile), imageExecutor
                )
            )
            .toList();

        return gatherFileNamesFromFutures(directory, futures);
    }

    private List<String> gatherFileNamesFromFutures(String directory, List<CompletableFuture<String>> futures) {
        List<String> fileNames = new ArrayList<>();
        AtomicBoolean catchException = new AtomicBoolean(false);
        futures.forEach(future -> {
            try {
                fileNames.add(future.join());
            } catch (CompletionException e) {
                catchException.set(true);
            }
        });
        handleException(catchException, directory, fileNames);
        return fileNames;
    }

    //이미지 업로드 중 예외 발생시 이미지 다시 삭제
    private void handleException(AtomicBoolean catchException, String directory, List<String> fileNames) {
        if (catchException.get()) {
            imageExecutor.execute(() -> delete(directory, fileNames));
            throw new AmazonS3Exception("이미지 업로드 에러 발생");
        }
    }

    public void delete(String directory, List<String> fileNames) {
        if (fileNames.isEmpty()) {
            return;
        }

        List<CompletableFuture<Void>> futures = fileNames.stream()
            .map(fileName ->
                CompletableFuture.runAsync(
                    () -> imageUploader.delete(directory, fileName), imageExecutor
                )
            ).toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            allFutures.join();
        } catch (CompletionException e) {
            //이미지 삭제 도중 실패할 경우 다시 업로드 하는 로직은 작성하지 않음.
            throw new AmazonS3Exception("이미지 삭제 에러 발생");
        }
    }

}
