package hello.kpop.image.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    //이미지 등록
    @PostMapping("/image")
    public ResponseEntity<ImageResponseDto> registerImage(ImageDto requestDto, @RequestParam(value="image") MultipartFile image) throws IOException {

        log.info("이미지 로그 ={}", image);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ImageResponseMessage.IMAGE_REGISTER_SUCCESS, imageService.registerImage(requestDto, image)), HttpStatus.OK);
    }
}
