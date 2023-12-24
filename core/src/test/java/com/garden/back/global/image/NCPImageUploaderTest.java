package com.garden.back.global.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.garden.back.global.MockTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class NCPImageUploaderTest extends MockTestSupport {

    @Mock
    AmazonS3 amazonS3;

    @Mock
    NCPProperties ncpProperties;

    @InjectMocks
    NCPImageUploader ncpImageUploader;

    @DisplayName("이미지를 네이버 클라우드에 업로드 한다.")
    @Test
    void upload() throws Exception {
        // Given
        String expected = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        given(amazonS3.putObject(any())).willReturn(new PutObjectResult());
        given(amazonS3.getUrl(anyString(), anyString())).willReturn(new URL(expected));
        given(ncpProperties.getBaseDirectory()).willReturn("/base/dir/");
        given(ncpProperties.getBucket()).willReturn("bucketName");

        MultipartFile mockFile = mock(MultipartFile.class);
        given(mockFile.getOriginalFilename()).willReturn("test.jpg");
        given(mockFile.getContentType()).willReturn("image/jpeg");
        given(mockFile.getSize()).willReturn(1024L);
        given(mockFile.getInputStream()).willReturn(new ByteArrayInputStream(new byte[1024]));

        // When
        String result = ncpImageUploader.upload("dir/", mockFile);

        // Then
        then(amazonS3).should().putObject(any(PutObjectRequest.class));
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("이미지를 네이버 클라우드에서 삭제 한다.")
    @Test
    void delete() throws Exception {
        //given
        String imageUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        URL url = new URL(imageUrl);
        String bucket = "bucket";
        String path = url.getPath().substring(1);
        String objectKey = URLDecoder.decode(path, StandardCharsets.UTF_8.name());

        given(ncpProperties.getBucket()).willReturn(bucket);

        doNothing().when(amazonS3).deleteObject(anyString(), anyString());
        // When
        ncpImageUploader.delete("dir/", imageUrl);

        // Then
        then(amazonS3).should().deleteObject(bucket, objectKey);
    }

    @DisplayName("이미지 업로드 중 예외가 발생한다.")
    @Test
    void upload_throwsException() throws IOException {
        // Given
        MultipartFile mockFile = mock(MultipartFile.class);
        given(mockFile.getOriginalFilename()).willReturn("test.jpg");
        given(mockFile.getContentType()).willReturn("image/jpeg");
        given(mockFile.getSize()).willReturn(1024L);
        given(mockFile.getInputStream()).willReturn(new ByteArrayInputStream(new byte[1024]));
        given(ncpProperties.getBaseDirectory()).willReturn("/base/dir/");
        given(ncpProperties.getBucket()).willReturn("bucketName");
        given(amazonS3.putObject(any(PutObjectRequest.class))).willThrow(new AmazonS3Exception("Upload failed"));

        // When & Then
        assertThrows(AmazonS3Exception.class, () -> {
            ncpImageUploader.upload("dir/", mockFile);
        });
    }

    @DisplayName("잘못된 URL 형식으로 인한 삭제 중 예외가 발생한다.")
    @Test
    void delete_throwsMalformedURLException() {
        // Given
        String invalidUrl = "htps://not.a.valid.url";

        // When & Then
        assertThrows(AmazonS3Exception.class, () -> {
            ncpImageUploader.delete("dir/", invalidUrl);
        });
    }

    @DisplayName("S3에서 객체 삭제 중 예외가 발생한다.")
    @Test
    void delete_throwsAmazonS3Exception() {
        // Given
        String imageUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        String bucket = "bucket";

        given(ncpProperties.getBucket()).willReturn(bucket);
        willThrow(new AmazonS3Exception("Delete failed")).given(amazonS3).deleteObject(anyString(), anyString());

        // When & Then
        assertThrows(AmazonS3Exception.class, () -> {
            ncpImageUploader.delete("dir/", imageUrl);
        });
    }
}