package com.garden.back.garden.repository.mymanagedgarden.dto;

import java.time.LocalDate;

public interface MyManagedGardenDetailRepositoryResponse {
    Long getMyManagedGardenId();
    String getMyManagedGardenName();
    LocalDate getCreatedAt();
    String getImageUrl();
    String getDescription();
}
