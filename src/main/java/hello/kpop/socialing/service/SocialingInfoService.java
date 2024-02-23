package hello.kpop.socialing.service;


import com.querydsl.core.BooleanBuilder;
import hello.kpop.socialing.QSocialing;
import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.Pagination;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.SocialingUtils;
import hello.kpop.socialing.dto.*;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import hello.kpop.socialing.repository.SocialingViewRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

//소셜링 조회(목록, 검색 , 페이징 ,조회수)

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class SocialingInfoService {


    private final SocialingRepository socialingRepository;
    private final HttpServletRequest request;
    private final SocialingViewRepository viewRepository;
    private final SocialingUtils utils;


    //단일 조회
//    public SocialingResponseDto findSocial(Long id) {
//        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);
//        return new SocialingResponseDto(socialing);
//    }

    public SocialViewData findSocialAndList(Long id){
        // 단일 조회
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        // 목록 데이터 가져오기
        List<Socialing> SocialViewCountAsc = socialingRepository.getSocialingView(socialing);
        List<Socialing> SocialLikeCountAsc = socialingRepository.getSocialingLike(socialing);

        // SocialViewData 객체 생성
        return SocialViewData.builder()
                .NicName(socialing.getUserId().getUserName())
                .artist_name(socialing.getArtistId().getArtistName())
                .socialing_name(socialing.getSocialing_name())
                .socialing_content(socialing.getSocialing_content())
                .quota(socialing.getQuota())
                .del_yn(socialing.getDel_yn())
                .start_date(socialing.getStart_date())
                .end_date(socialing.getEnd_date())
                .view_count(socialing.getView_ctn())
                .ViewCountAsc(SocialViewCountAsc)
                .LikeCountAsc(SocialLikeCountAsc)
                .build();
    }


    //목록 조회
    //검색과 페이징 처리
    public ListData<Socialing> getList(SocialingSearchDto searchDto){
        int page = ProcessUtils.onlyPositiveNumber(searchDto.getPage(),1);
        int limit = ProcessUtils.onlyPositiveNumber(searchDto.getLimit(), 20);

        //검색 처리
        QSocialing socialing = QSocialing.socialing;

        BooleanBuilder andBuilder = new BooleanBuilder();

        String sopt = Objects.requireNonNullElse(searchDto.getSopt(),"ALL"); // 검색 옵션
        String skey = searchDto.getSkey(); // 검색 키워드

        //조건 키워드 검색
        if(StringUtils.hasText(skey) ){
        skey=skey.trim();
            if(sopt.equals("social_name")){ //제목으로 검색
                andBuilder.and(socialing.socialing_name.contains(skey));

            } else if(sopt.equals("socialing_place")){ //지역으로 검색
                andBuilder.and(socialing.social_place.contains(skey));

            } else if(sopt.equals("artist")){  //아티스트 이름 검색
                andBuilder.and(socialing.artistId.artistName.contains(skey) );

            } else if(sopt.equals("sname_sartist")){ //소셜링 제목 + 아티스트
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(socialing.socialing_name.contains(skey))
                         .or(socialing.artistId.artistName.contains(skey));
                         andBuilder.and(orBuilder);

            } else if(sopt.equals("ALL")){ //통합 검색 (제목 지역 아티스트 )
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(socialing.socialing_name.contains(skey))
                         .or(socialing.social_place.contains(skey))
                         .or(socialing.artistId.artistName.contains(skey));
                         andBuilder.and(orBuilder);
            }
        }

        //특정 타입 or 지역으로 한정 검색
        String type = searchDto.getType();
        String socialPlace = searchDto.getSocialPlace();
        if (StringUtils.hasText(type)) {
            type=type.trim();
            andBuilder.and(socialing.type.eq(type));
        }

        if (StringUtils.hasText(socialPlace)) {
            socialPlace=socialPlace.trim();
            andBuilder.and(socialing.social_place.eq(socialPlace));
        }

        // 페이징
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Socialing> data = socialingRepository.findAll(andBuilder, pageable);
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        ListData<Socialing> listData = new ListData<>();
        listData.setContent(data.getContent());
        listData.setPagination(pagination);
        return listData;
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

        socialing.setView_ctn(viewCount);
        viewRepository.flush();

    }


}
