package com.garden.back.garden.repository.mymanagedgarden.dto;

import java.time.LocalDate;

public interface MyManagedGardensGetRepositoryResponse {
    String getMyManagedGardenName();
    Long getMyManagedGardenId();
    LocalDate getCreatedAt();
    String getImageUrl();
    String getDescription();

}
