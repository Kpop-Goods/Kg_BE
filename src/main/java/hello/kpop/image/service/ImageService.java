package hello.kpop.image.service;

import hello.kpop.agency.Agency;
import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.artist.Artist;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.board.Board;
import hello.kpop.board.repository.BoardRepository;
import hello.kpop.global.s3.S3Uploader;
import hello.kpop.goods.dto.Goods;
import hello.kpop.goods.repository.GoodsRepository;
import hello.kpop.image.Image;
import hello.kpop.image.dto.ImageDto;
import hello.kpop.image.dto.ImageResponseDto;
import hello.kpop.image.exception.ProfileImageLimitExceededException;
import hello.kpop.image.repository.ImageRepository;
import hello.kpop.place.Place;
import hello.kpop.place.repository.PlaceRepository;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final AgencyRepository agencyRepository;
    private final BoardRepository boardRepository;
    private final PlaceRepository placeRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    // 기존의 이미지 등록 로직을 수정하여 엔티티 타입과 ID를 포함하도록 확장
    public void registerImage(Integer entityType, Long entityId, List<String> imgPaths) {

        if (imgPaths.isEmpty()) {
            throw new IllegalArgumentException("이미지 경로가 제공되지 않았습니다.");
        }

        // 엔티티 타입과 ID에 따라 이미지를 등록하는 로직을 구현
        switch (entityType) {
            case 10: // user
                // 유저 프로필 이미지 등록 로직

                if (imgPaths.size() > 1) {
                    throw new ProfileImageLimitExceededException("프로필 이미지는 하나만 등록할 수 있습니다.");
                }

                String imgSingleUrl = imgPaths.get(0);
                Image userImg = new Image(imgSingleUrl, entityType, entityId);
                imageRepository.save(userImg);

                User user = userRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + entityId));

                user.updateProfileImageUrl(imgSingleUrl);
                break;
            case 20: // goods
                // 굿즈 이미지 등록 로직

                List<String> goodsImgList = new ArrayList<>();
                for (String goodsImgUrl : imgPaths) {
                    Image goodsImg = new Image(goodsImgUrl, entityType, entityId);
                    imageRepository.save(goodsImg);
                    goodsImgList.add(goodsImg.getImageUrl());
                }

                Goods goods = goodsRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + entityId));

                goods.updateImageUrls(goodsImgList);
                break;
            case 30: // board
                // 게시물 이미지 등록 로직

                List<String> boardImgList = new ArrayList<>();
                for (String boardImgUrl : imgPaths) {
                    Image boardImg = new Image(boardImgUrl, entityType, entityId);
                    imageRepository.save(boardImg);
                    boardImgList.add(boardImg.getImageUrl());
                }

                Board board = boardRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다:" + entityId));

                board.updateImageUrls(boardImgList);
                break;
            case 40: // event
                // 이벤트 이미지 등록 로직

                List<String> eventImgList = new ArrayList<>();
                for (String eventImgUrl : imgPaths) {
                    Image eventImg = new Image(eventImgUrl, entityType, entityId);
                    imageRepository.save(eventImg);
                    eventImgList.add(eventImg.getImageUrl());
                }

                Place event = placeRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 이벤트를 찾을 수 없습니다:" + entityId));

                event.updateImageUrls(eventImgList);

                break;
            case 50: // artist
                // 아티스트 이미지 등록 로직

                List<String> artistImgList = new ArrayList<>();
                for (String artistImgUrl : imgPaths) {
                    Image artistImg = new Image(artistImgUrl, entityType, entityId);
                    imageRepository.save(artistImg);
                    artistImgList.add(artistImg.getImageUrl());
                }

                Artist artist = artistRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 아티스트를 찾을 수 없습니다:" + entityId));

                artist.updateImageUrls(artistImgList);


                break;
            case 60: // agency
                // 소속사 이미지 등록 로직

                List<String> agencyImgList = new ArrayList<>();
                for (String agencyImgUrl : imgPaths) {
                    Image agencyImg = new Image(agencyImgUrl, entityType, entityId);
                    imageRepository.save(agencyImg);
                    agencyImgList.add(agencyImg.getImageUrl());
                }

                Agency agency = agencyRepository.findById(entityId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 소속사를 찾을 수 없습니다:" + entityId));

                agency.updateImageUrls(agencyImgList);

                break;
            default:
                throw new IllegalArgumentException("Invalid entity type: " + entityType);
        }
    }
//    public void registerImage(List<String> multipartFiles) {
//        List<String> imgList = new ArrayList<>();
//        for (String imgUrl : multipartFiles) {
//            Image img = new Image(imgUrl);
//            imageRepository.save(img);
//            imgList.add(img.getImageUrl());
//        }
//    }
}
