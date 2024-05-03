package hello.kpop.image.controller;

import hello.kpop.global.s3.S3Uploader;
import hello.kpop.image.dto.ImageDto;
import hello.kpop.image.dto.ImageResponseDto;
import hello.kpop.image.errorHandler.ImageResponseMessage;
import hello.kpop.image.service.ImageService;
import hello.kpop.place.common.DefaultRes;
import hello.kpop.place.common.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;
    private final S3Uploader s3Uploader;

    /*
     * 한번에 여러 개의 이미지 파일 업로드
     * */
    @PostMapping("/file/uploadFile")
    public ResponseEntity<ImageResponseDto> registerImag(@RequestPart("imgUrl") List<MultipartFile> multipartFiles) {
        List<String> imgPaths = s3Uploader.uploadFile(multipartFiles);
        imageService.registerImage(imgPaths);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ImageResponseMessage.IMAGE_REGISTER_SUCCESS, s3Uploader.uploadFile(multipartFiles)), HttpStatus.OK);
    }

    /*
     * 한 개의 이미지 파일 삭제
     * 파일 이름에 기반하여 S3에 올라가있는 파일을 삭제
     * */
    @DeleteMapping("/file/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName){
        s3Uploader.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}
