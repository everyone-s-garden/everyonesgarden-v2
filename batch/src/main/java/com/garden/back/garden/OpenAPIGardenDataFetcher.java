package com.garden.back.garden;

import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.OpenAPIGardenCommandService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OpenAPIGardenDataFetcher {

    private final OpenAPIGardenInfoClient openAPIGardenInfoClient;
    private final OpenAPIGardenCommandService openAPIGardenCommandService;
    private final GardenCommandService gardenCommandService;

    public OpenAPIGardenDataFetcher(OpenAPIGardenInfoClient openAPIGardenInfoClient,
                                    OpenAPIGardenCommandService openAPIGardenCommandService,
                                    GardenCommandService gardenCommandService) {
        this.openAPIGardenInfoClient = openAPIGardenInfoClient;
        this.openAPIGardenCommandService = openAPIGardenCommandService;
        this.gardenCommandService = gardenCommandService;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void run() {
        FarmResponse response = openAPIGardenInfoClient.getFarmFromOpenAPI();

        openAPIGardenCommandService.updateOpenAPIGarden(response.toOpenAPIGardenUpdateParam());
        gardenCommandService.updateGardenFromOpenAPI(
            openAPIGardenCommandService.findAll().toGardenUpdateFromOpenAPIParams());
    }

}
