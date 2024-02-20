package com.garden.back.garden.repository.mymanagedgarden.dto;

import java.time.LocalDate;

public interface MyManagedGardenDetailRepositoryResponse {
    Long getMyManagedGardenId();
    String getGardenName();
    String getAddress();
    LocalDate getUseStartDate();
    LocalDate getUseEndDate();
    String getImageUrl();
    String getDescription();
}
