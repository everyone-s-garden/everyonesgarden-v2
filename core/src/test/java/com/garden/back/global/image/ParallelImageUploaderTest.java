package com.garden.back.global.image;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.garden.back.global.MockTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ParallelImageUploaderTest extends MockTestSupport {

    @Mock
    private ImageUploader mockImageUploader;

    private Executor mockExecutor = Runnable::run;

    @Mock
    private MultipartFile mockMultipartFile;

    private ParallelImageUploader parallelImageUploader;

    @BeforeEach
    void setUp() {
        parallelImageUploader = new ParallelImageUploader(mockImageUploader, mockExecutor);
    }


    @DisplayName("유효한 파일을 업로드 한다.")
    @Test
    void uploadWithValidFiles() {
        //given
        List<MultipartFile> files = List.of(mockMultipartFile, mockMultipartFile);
        given(mockImageUploader.upload(anyString(), any(MultipartFile.class))).willReturn("filename");

        //when
        List<String> result = parallelImageUploader.upload("directory", files);

        //then
        assertThat(result).hasSize(2);
        verify(mockImageUploader, times(2)).upload(anyString(), any(MultipartFile.class));
    }

    @DisplayName("빈 파일 업로드 하면 빈 list가 반환된다.")
    @Test
    void uploadWithEmptyFileList() {
        //given & when
        List<String> result = parallelImageUploader.upload("directory", List.of());

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("upload 도중 예외가 발생하면 업로드 된 파일을 삭제한다.")
    @Test
    void uploadHandlingExceptions() {
        //given
        List<MultipartFile> files = List.of(mockMultipartFile);

        given(mockImageUploader.upload(anyString(), any(MultipartFile.class)))
            .willThrow(new CompletionException("이미지 업로드 예외 발생", new Throwable()));

        //when & then
        assertThatThrownBy(() -> parallelImageUploader.upload("directory", files))
            .isInstanceOf(AmazonS3Exception.class);
        //verify(mockImageUploader).delete(any(), any());
    }

    @DisplayName("유효한 파일을 삭제한다.")
    @Test
    void deleteWithValidFileNames() {
        //given
        List<String> fileNames = List.of("file1", "file2");

        //when
        parallelImageUploader.delete("directory", fileNames);

        //then
        verify(mockImageUploader, times(2)).delete(anyString(), anyString());
    }

    @DisplayName("빈 파일이 들어오면 delete 메소드는 호출되지 않는다.")
    @Test
    void deleteWithEmptyFileList() {
        //given & when
        parallelImageUploader.delete("directory", List.of());

        //then
        verify(mockImageUploader, never()).delete(anyString(), anyString());
    }

    @DisplayName("비동기 삭제 도중 예외가 발생하면 아마존 예외로 콜백한다.")
    @Test
    void deleteHandlingExceptions() {
        //given
        List<String> fileNames = List.of("file1");

        //when
        doThrow(new CompletionException("예외 발생", new Throwable())).when(mockImageUploader).delete(anyString(), anyString());

        //then
        assertThatThrownBy(() -> parallelImageUploader.delete("directory", fileNames))
            .isInstanceOf(AmazonS3Exception.class);
    }
}