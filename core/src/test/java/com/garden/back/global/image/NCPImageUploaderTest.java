package com.garden.back.global.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.garden.back.global.MockTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
}