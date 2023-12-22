package com.garden.back.global.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    /**
     * 이미지 업로드 시 사용
     * @param directory : image가 업로드 되어야 하는 파일 경로 디렉토리는 폴터명/으로 시작하며 /로 끝나야 한다.
     *                  e.g) "feedback/"
     * @param multipartFile : 이미지
     * @return 저장된 이미지 주소 리스트
     */
    String upload(String directory, MultipartFile multipartFile);

    /**
     * 이미지 삭제 시 사용
     * @param directory : image가 업로드 되어야 하는 파일 경로 디렉토리는 폴터명/으로 시작하며 /로 끝나야 한다.
     *                  e.g) "member/"
     * @param imageUrl : Naver Object Storage에 image 저장 후 반환된 경로 리스트
     */
    void delete(String directory, String imageUrl);

}
