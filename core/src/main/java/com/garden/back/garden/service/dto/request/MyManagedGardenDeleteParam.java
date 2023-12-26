package com.garden.back.garden.service.dto.request;

public record MyManagedGardenDeleteParam (
        Long myManagedGardenId,
        Long memberId
) {
    public static MyManagedGardenDeleteParam of(Long myManagedGardenId, Long memberId) {
        return new MyManagedGardenDeleteParam(
                myManagedGardenId,
                memberId
        );
    }
}
