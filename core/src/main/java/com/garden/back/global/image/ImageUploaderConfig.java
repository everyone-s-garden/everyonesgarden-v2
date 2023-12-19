package com.garden.back.global.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageUploaderConfig {

    private final NCPProperties ncpProperties;

    public ImageUploaderConfig(NCPProperties ncpProperties) {
        this.ncpProperties = ncpProperties;
    }

    @Bean
    public AmazonS3 ncpUploader() {
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ncpProperties.getUrl(), ncpProperties.getRegion()))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ncpProperties.getAccessKey(), ncpProperties.getSecretKey())))
            .build();
    }
}
