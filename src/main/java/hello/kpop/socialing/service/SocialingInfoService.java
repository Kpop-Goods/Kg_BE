package hello.kpop.socialing.service;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.socialing.QSocialing;
import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.*;
import hello.kpop.socialing.dto.*;
import hello.kpop.socialing.exception.SocialingDataNotFoundException;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import hello.kpop.socialing.repository.SocialingViewRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.desc;

//소셜링 조회(목록, 검색 , 페이징 ,조회수)

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class SocialingInfoService {


    private final SocialingRepository socialingRepository;
    private final HttpServletRequest request;
    private final SocialingViewRepository viewRepository;
    private final ModelMapper modelMapper;
    private final SocialingUtils utils;
    private final JPAQueryFactory jpaQueryFactory;


    //단일 조회
    public SocialViewData findSocial(Long id){
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);
        SocialingStatus yn = socialing.getDel_yn();
        if (yn.equals(SocialingStatus.COMPLETE)) {
            throw new SocialingDataNotFoundException();
        }
        String name= socialing.getArtist().getArtistName();
        String agency = socialing.getArtist().getAgency().getAgencyName();

        modelMapper.typeMap(Socialing.class, SocialViewData.class).addMappings(mapper -> {
            if(StringUtils.hasText(name)){
                mapper.map(s -> s.getArtist().getArtistName(), SocialViewData::setArtistName);
            }
            if(StringUtils.hasText(agency)){
                mapper.map(s -> s.getArtist().getAgency().getAgencyName(), SocialViewData::setAgency);
            }
        });

        SocialViewData socialViewData = modelMapper.map(socialing, SocialViewData.class);
        socialViewData.setNickname(socialing.getUser().getNickname());

        // 조회수 순으로 10개의 데이터를 가져옴
        socialViewData.setViewDesc(getView(id));

        // 좋아요 순으로 10개의 데이터를 가져옴
        socialViewData.setLikeDesc(getLike(id));

        return socialViewData;
    }
    //조회수 높은순으로 목록 가져오기
    public List<SocialingListData> getView(Long id) {
        return socialingRepository.findAllByOrderByViewDesc(PageRequest.of(0, 10)).stream()
                .filter(data -> !data.getSocialingId().equals(id))
                .filter(data -> !data.getDel_yn().equals(SocialingStatus.COMPLETE))
                .map(SocialingListData::new)
                .collect(Collectors.toList());
    }
    // 좋아요 높은 순으로 목록 가져오기
    public List<SocialingListData> getLike(Long id) {
        return socialingRepository.findAllByOrderByLikeDesc(PageRequest.of(0, 10)).stream()
                .filter(data -> !data.getSocialingId().equals(id))
                .filter(data -> !data.getDel_yn().equals(SocialingStatus.COMPLETE))
                .map(SocialingListData::new)
                .collect(Collectors.toList());
    }

    //목록 조회 및 검색과 페이징 처리
    public ListData<SocialingListData> getList(SocialingSearchDto searchDto){
        int page = ProcessUtils.onlyPositiveNumber(searchDto.getPage(),1);
        int limit = ProcessUtils.onlyPositiveNumber(searchDto.getLimit(), 20);
        int offset = (page - 1) * limit;

        //검색 처리
        QSocialing socialing = QSocialing.socialing;

        BooleanBuilder andBuilder = new BooleanBuilder();

       // String sopt = Objects.requireNonNullElse(searchDto.getSopt(),"ALL"); // 검색 옵션
        String sopt = searchDto.getSopt();
        String skey = searchDto.getSkey(); // 검색 키워드

        //조건별 키워드 검색
        if(StringUtils.hasText(skey) ){
            skey=skey.trim();

            BooleanExpression name = socialing.socialing_name.contains(skey);
            BooleanExpression place = socialing.social_place.contains(skey);
            BooleanExpression type = socialing.type.contains(skey);
            BooleanExpression artist = socialing.artist.artistName.contains(skey);

            if(sopt.equals("name")){ //제목으로 검색
                    andBuilder.and(name);

            } else if(sopt.equals("place")){ //지역으로 검색
                    andBuilder.and(place);

            } else if(sopt.equals("artist")){  //아티스트 이름 검색
                    andBuilder.and(artist);

            } else if(sopt.equals("type")){ //타입으로 검색
                    andBuilder.and(type) ;

            } else if(sopt.equals("ALL")){ //통합 검색 (제목 지역 타입 아티스트 )
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(name)
                             .or(place)
                             .or(type)
                             .or(artist);
                    andBuilder.and(orBuilder);
            }
        }

            String name = searchDto.getName();
            String artist = searchDto.getArtist();
            String type = searchDto.getType();
            String place = searchDto.getPlace();

            if (StringUtils.hasText(name)) {
                name = name.trim();
                andBuilder.and(socialing.socialing_name.eq(name));
            }
            if (StringUtils.hasText(artist)) {
                artist = artist.trim();
                andBuilder.and(socialing.artist.artistName.eq(artist));
            }
            if (StringUtils.hasText(type)) {
                type = type.trim();
                andBuilder.and(socialing.type.eq(type));
            }
            if (StringUtils.hasText(place)) {
                andBuilder.and(socialing.social_place.contains(place.trim()));
            }


        List<SocialingListData> items =  jpaQueryFactory
                .selectFrom(socialing)
                .leftJoin(socialing.user)
                .fetchJoin()
                .leftJoin(socialing.artist)
                .fetchJoin()
                .leftJoin(socialing.artist.agency)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch()
                .stream()
                .map(SocialingListData::new)
                .collect(Collectors.toList());

       // long total = socialingRepository.count(andBuilder);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("regDt")));
        Page<Socialing>  data = socialingRepository.findAll(andBuilder,pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);
        ListData<SocialingListData> listData = new ListData<>();
        listData.setContent(items);
        listData.setPagination(pagination);

        filterList(listData);

        return listData;
    }

    //목록 데이터 필터링 (모집이 완료된 소셜링 데이터 필터)
    public void filterList(ListData<SocialingListData> dataList) {
        List<SocialingListData> content = dataList.getContent();

        List<SocialingListData> filteredList = content.stream()
                .filter(data -> !data.getDel_yn().equals(SocialingStatus.COMPLETE))
                .collect(Collectors.toList());
        dataList.setContent(filteredList);
    }



    //조회수 구현
    public void socialingViewCount(Long id){
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        if( socialing ==null) return;
        try{
            int uid = utils.isLogin() ?utils.getUser().getId().intValue() : utils.guestId();

            SocialingView socialingView = new SocialingView(id,uid);

            viewRepository.saveAndFlush(socialingView);

        }catch (Exception e){}

        //조회수 카운팅 후 업데이트 ( querydsl )
        QSocialingView sv = QSocialingView.socialingView;
        int  viewCount = (int) viewRepository.count(sv.id.eq(id));

        socialing.setView(viewCount);
        viewRepository.flush();

    }


}
