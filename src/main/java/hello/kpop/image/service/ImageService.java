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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ImageResponseDto registerImage(ImageDto requestDto, MultipartFile image) throws IOException {

        Image imageEntity = new Image();

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            requestDto.setImageUrl(storedFileName);
        }

        Image savedImage = new Image(requestDto);
        imageRepository.save(savedImage);

        return new ImageResponseDto(savedImage);

    }
}
