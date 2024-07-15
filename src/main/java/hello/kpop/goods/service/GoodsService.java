package hello.kpop.goods.service;

import hello.kpop.agency.status.DelStatus;
import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.goods.GoodsSpecification;
import hello.kpop.goods.dto.Goods;
import hello.kpop.goods.dto.GoodsRequestDto;
import hello.kpop.goods.dto.GoodsResponseDto;
import hello.kpop.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final ArtistRepository artistRepository;

    //굿즈 등록
    @Transactional
    public GoodsResponseDto saveGoods(GoodsRequestDto requestDto, Long id) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        Goods goods = new Goods(requestDto, artist);

        //등록 시 삭제여부는 N으로 설정
        goods.updateDelYN(DelStatus.DEFAULT);

        goodsRepository.save(goods);

        return new GoodsResponseDto(goods);
    }

    //페이징 + 굿즈 전체 조회
    @Transactional(readOnly = true)
    public Page<Goods> pageGoodsList(int page, int size) {
        //삭제여부 "Y"인 데이터 가져옴
        List<Goods> goods = goodsRepository.findByDelYN("Y");

        //전체 데이터 조회
        Pageable pageable = PageRequest.of(page, size, Sort.by("goodsId").descending());
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);

        //"Y"인 데이터를 제외한 후 반환
        List<Goods> filteredGoods = goodsPage.getContent().stream()
                .filter(goods1 -> !goods.contains(goods1))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredGoods, pageable, goodsPage.getTotalElements());
    }

    //선택한 굿즈 상세 조회
    @Transactional
    public GoodsResponseDto selectGoodsOne(Long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("굿즈 ID가 존재하지 않습니다."));

        //만약 해당 굿즈가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(goods.getGoodsId().equals(id) && goods.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 굿즈입니다.");
        }

        //조회수 +1
        int currentCount = goods.getCount();
        goods.setCount(currentCount + 1);

        return new GoodsResponseDto(goods);
    }

    //선택한 굿즈 정보 수정
    @Transactional
    public GoodsResponseDto updateGoods(Long id, GoodsRequestDto requestDto) throws Exception {
        Goods goods = goodsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("굿즈 ID가 존재하지 않습니다."));

        //만약 해당 굿즈가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(goods.getGoodsId().equals(id) && goods.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 굿즈입니다.");
        }

        goods.update(requestDto);
        return new GoodsResponseDto(goods);
    }

    //선택한 굿즈 삭제
    @Transactional
    public SuccessResponseDto deleteGoods(Long id) throws Exception {
        Goods goods = goodsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("굿즈 ID가 존재하지 않습니다."));

        goods.updateDelYN(DelStatus.DELETE);
        return new SuccessResponseDto(true);
    }

    //이벤트명, 장소 카테고리 코드, 아티스트코드, 소속사코드 검색했을 시 조회
    @Transactional
    public List<GoodsResponseDto> searchGoods(String goodsName, String goodsCategoryCd) {
        Specification<Goods> spec = (root, query, criteriaBuilder) -> null;
        Specification<GoodsResponseDto> specification = (root, query, criteriaBuilder) -> null;

        if (goodsName != null) {
            spec = spec.and(GoodsSpecification.equalGoodsName(goodsName));
        }
        if (goodsCategoryCd != null) {
            spec = spec.and(GoodsSpecification.equalGoodsCategoryCode(goodsCategoryCd));
        }

        // 'delYN'이 'Y'인 데이터를 제외하기 위한 스펙 추가
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("delYN"), "Y"));

//        log.info("service : {}", spec);
//        log.info("service2 : {}", placeRepository.findAll(spec).stream().map(PlaceResponseDto::new).toList());
        return goodsRepository.findAll(spec).stream().map(GoodsResponseDto::new).toList();
    }
}
