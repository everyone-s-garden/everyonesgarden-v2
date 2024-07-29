package com.garden.back.garden;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "${api.mafra.name}",
    url = "${api.mafra.url}"
)
public interface OpenAPIGardenInfoClient {

    @GetMapping
    FarmResponse getFarmFromOpenAPI();
}
