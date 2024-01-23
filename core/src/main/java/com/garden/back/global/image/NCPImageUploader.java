package com.garden.back.global.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class NCPImageUploader implements ImageUploader {

    private static final String EMPTY_IMAGE_URL = "";
    private final NCPProperties ncpProperties;
    private final AmazonS3 ncpUploader;

    public NCPImageUploader(NCPProperties ncpProperties, AmazonS3 ncpUploader) {
        this.ncpProperties = ncpProperties;
        this.ncpUploader = ncpUploader;
    }

    @Override
    public String upload(String directory, MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return EMPTY_IMAGE_URL;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = Objects.requireNonNull(multipartFile.getContentType());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(contentType);

        try {
            String path = ncpProperties.getBaseDirectory() + directory + originalFilename; //ncp 버킷 내에 baseDirectory에 있는 directory 경로에 originalFilename이라는 이름으로 저장 예: /asd/fgh/efg.jpg 와 같이 저장 됨
            PutObjectRequest putObjectRequest = new PutObjectRequest(ncpProperties.getBucket(), path, multipartFile.getInputStream(), objectMetadata);
            ncpUploader.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
            return ncpUploader.getUrl(ncpProperties.getBucket(), path).toString();
        } catch (Exception e) {
            throw new AmazonS3Exception("이미지 업로드 중 오류 발생");
        }
    }

    @Override
    public void delete(String directory, String imageUrl) {
        if(imageUrl.isEmpty()) {
            return;
        }

        try {
            URL url = new URL(imageUrl);
            String path = refineUrl(url.getPath());
            String bucketName = ncpProperties.getBucket();

            // 파일명이 한글이면 인코딩 된 파일이 저장되므로 디코딩이 필요함.
            String objectKey = URLDecoder.decode(path, StandardCharsets.UTF_8.name());

            ncpUploader.deleteObject(bucketName, objectKey);
        } catch (MalformedURLException e) {
            throw new AmazonS3Exception("잘못된 URL 형식입니다.");
        } catch (UnsupportedEncodingException e) {
            throw new AmazonS3Exception("URL 디코딩 중 오류 발생");
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception("S3에서 객체 삭제 중 오류 발생");
        }
    }

    private String refineUrl(String path) {
        return path.substring(1);
    }

}
