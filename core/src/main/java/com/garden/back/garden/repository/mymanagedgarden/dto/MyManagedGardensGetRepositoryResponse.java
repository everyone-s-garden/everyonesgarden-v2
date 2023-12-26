package com.garden.back.garden.repository.mymanagedgarden.dto;

import java.time.LocalDate;

public interface MyManagedGardensGetRepositoryResponse {

    Long getMyManagedGardenId();
    String getGardenName();
    LocalDate getUseStartDate();
    LocalDate getUseEndDate();
    String getImageUrl();

}
