package hello.kpop.image.service;

import hello.kpop.global.s3.S3Uploader;
import hello.kpop.image.Image;
import hello.kpop.image.dto.ImageDto;
import hello.kpop.image.dto.ImageResponseDto;
import hello.kpop.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public void registerImage(List<String> multipartFiles) {
        List<String> imgList = new ArrayList<>();
        for (String imgUrl : multipartFiles) {
            Image img = new Image(imgUrl);
            imageRepository.save(img);
            imgList.add(img.getImageUrl());
        }
    }
}
