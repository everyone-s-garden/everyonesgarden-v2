package com.garden.back.global.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cloud.ncp")
@Component
@Getter
@Setter
public class NCPProperties {
    private String url;
    private String region;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String baseDirectory;
}
