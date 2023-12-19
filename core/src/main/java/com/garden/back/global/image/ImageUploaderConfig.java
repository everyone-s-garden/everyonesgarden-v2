package com.garden.back.global.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

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

    @Bean
    public Executor imageExecutor(
        @Value("${async.image.corePoolSize}") Integer corePoolSize,
        @Value("${async.image.maxPoolSize}") Integer maxPoolSize,
        @Value("${async.image.queCapacity}") Integer queCapacity,
        @Value("${async.image.keepAliveSeconds}") Integer keepAliveSeconds,
        @Value("${async.image.threadName}") String threadName
    ) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize); // 스레드 풀의 기본 스레드 수
        executor.setMaxPoolSize(maxPoolSize); // 스레드 풀의 최대 스레드 수
        executor.setQueueCapacity(queCapacity); // 작업 큐의 용량
        executor.setKeepAliveSeconds(keepAliveSeconds); // 최대 스레드 수가 기본 스레드 수를 초과할 때, 초과한 스레드가 유휴 상태로 있을 수 있는 최대 시간(초)
        executor.setThreadNamePrefix(threadName); // 생성되는 스레드의 이름 접두사
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //백그라운드 스레드가 다 찼을 경우 동기적으로 돌아가게 하는 정책, 이 설정도 외부에서 주입 받으면 좋은데 다른 코드에서 비동기 설정을 사용할 경우 추상화해서 주입 받겠음.
        executor.initialize();
        return executor;
    }
}
