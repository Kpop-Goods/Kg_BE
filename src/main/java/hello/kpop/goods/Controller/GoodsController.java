package hello.kpop.goods.Controller;

import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.goods.dto.Goods;
import hello.kpop.goods.dto.GoodsRequestDto;
import hello.kpop.goods.dto.GoodsResponseDto;
import hello.kpop.goods.errorHandler.GoodsResponseMessage;
import hello.kpop.goods.repository.GoodsRepository;
import hello.kpop.goods.service.GoodsService;
import hello.kpop.place.common.DefaultRes;
import hello.kpop.place.common.MultiResponseDto;
import hello.kpop.place.common.PageResponseDto;
import hello.kpop.place.common.StatusCode;
import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final GoodsRepository goodsRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    //굿즈 등록
    @PostMapping ("/goods/{artistId}")
    public ResponseEntity<GoodsResponseDto> saveGoods(@RequestBody GoodsRequestDto requestDto, @PathVariable Long artistId/*, Authentication authentication*/) throws Exception {
        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
//        if(authentication == null) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_REGISTER), HttpStatus.UNAUTHORIZED);
//        }

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.NOT_FOUND_ARTIST_ID), HttpStatus.BAD_REQUEST);
        }

        //굿즈 카테고리 코드 입력되지 않았을 시 실행
        if(requestDto.getGoodsCategoryCd() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.GOODS_CATEGORY_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //굿즈명 입력되지 않았을 시 실행
        if(requestDto.getGoodsName() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.NAME_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //굿즈 가격 입력되지 않았을 시 실행
        if(requestDto.getGoodsPrice() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.PRICE_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);
//
//        if (user.getUserType() == Role.USER) {
//            requestDto.updateRegId(user.getNickname());
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.GOODS_REGISTER_SUCCESS, goodsService.saveGoods(requestDto, artistId)), HttpStatus.OK);
////            return new ResponseEntity(goodsService.saveGoods(requestDto, artistId), HttpStatus.OK);
//        } else {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_REGISTER), HttpStatus.UNAUTHORIZED);
//        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.GOODS_REGISTER_SUCCESS, goodsService.saveGoods(requestDto, artistId)), HttpStatus.OK);
    }

    //페이징 + 굿즈 전체 조회
    @GetMapping("/goods/list")
    public ResponseEntity<GoodsResponseDto> pagePlace(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                      @RequestParam(required = false, defaultValue = "30", value = "size") int size) {

        //page, size가 음수일 경우
        if(page < 0 || size <= 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.NON_NEGATIVE_VALUES), HttpStatus.BAD_REQUEST);
        }

        Page<Goods> result = goodsService.pageGoodsList(page -1, size);
        List<Goods> list = result.getContent();

        return new ResponseEntity(new PageResponseDto<>(list, result), HttpStatus.OK);
    }

    //굿즈 상세 조회
    @GetMapping("/goods/one/{goodsId}")
    public ResponseEntity<GoodsResponseDto> selectGoodsOne(@PathVariable Long goodsId) {
        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);

        //굿즈 ID가 일치하는 게 없을 시 실행
        if(!(goodsRepository.findById(goodsId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.NOT_FOUND_GOODS_ONE), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Goods goods = goodsOptional.get();
        String delYN = goods.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, GoodsResponseMessage.DELETED_GOODS), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.FOUND_GOODS_ONE_SUCCESS, goodsService.selectGoodsOne(goodsId)), HttpStatus.OK);
    }

    //선택한 굿즈 정보 수정
    @PutMapping("/goods/{goodsId}")
    public ResponseEntity<GoodsResponseDto> updateGoods(@PathVariable Long goodsId, @RequestBody GoodsRequestDto requestDto/*, Authentication authentication*/) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
//        if(authentication == null) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_UPDATE), HttpStatus.UNAUTHORIZED);
//        }

        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);

        //굿즈 ID가 일치하는 게 없을 시 실행
        if(!(goodsRepository.findById(goodsId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.UPDATE_GOODS_FAIL), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Goods goods = goodsOptional.get();
        String delYN = goods.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, GoodsResponseMessage.DELETED_GOODS), HttpStatus.NOT_FOUND);
        }

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);
//
//        //이벤트를 등록한 사람만 수정 가능
//        if(goods.getRegId().equals(user.getNickname())) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.UPDATE_GOODS_SUCCESS, goodsService.updateGoods(goodsId, requestDto)), HttpStatus.OK);
//        } else {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_UPDATE), HttpStatus.UNAUTHORIZED);
//        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.UPDATE_GOODS_SUCCESS, goodsService.updateGoods(goodsId, requestDto)), HttpStatus.OK);
    }

    //선택한 굿즈 삭제
    @DeleteMapping("/goods/{goodsId}")
    public ResponseEntity<SuccessResponseDto> deleteGoods(@PathVariable Long goodsId/*, Authentication authentication*/) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
//        if(authentication == null) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_DELETE), HttpStatus.UNAUTHORIZED);
//        }

        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);
        Goods goods = goodsOptional.get();

        //이벤트 ID가 일치하는 게 없을 시 실행
        if(!(goodsRepository.findById(goodsId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, GoodsResponseMessage.DELETE_GOODS_FAIL), HttpStatus.BAD_REQUEST);
        }

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);
//
//        if(goods.getRegId().equals(user.getNickname())) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.DELETE_GOODS_SUCCESS, goodsService.deleteGoods(goodsId)), HttpStatus.OK);
//        } else  {
//            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, GoodsResponseMessage.UNAUTHORIZED_GOODS_DELETE), HttpStatus.UNAUTHORIZED);
//        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, GoodsResponseMessage.DELETE_GOODS_SUCCESS, goodsService.deleteGoods(goodsId)), HttpStatus.OK);
    }

    //굿즈명, 굿즈카테고리 검색했을 시 조회
    @GetMapping("/goods/search")
    public ResponseEntity findGoods(
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "goodsCategoryCd", required = false) String goodsCategoryCd) {

        List<GoodsResponseDto> lists = goodsService.searchGoods(goodsName, goodsCategoryCd);
        MultiResponseDto<GoodsResponseDto> responseDto = MultiResponseDto.<GoodsResponseDto>builder().data(lists).build();
        return ResponseEntity.ok().body(responseDto);
    }
}
