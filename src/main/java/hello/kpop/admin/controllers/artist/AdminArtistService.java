package hello.kpop.admin.controllers.artist;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.artist.Artist;
import hello.kpop.artist.QArtist;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.Pagination;
import hello.kpop.socialing.common.ProcessUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminArtistService {

    private final JPAQueryFactory jpaQueryFactory;
    private final ArtistRepository artistRepository;
    private final HttpServletRequest request;


    public ListData<ArtistResponseDto> getList(SearchData searchData){
        int page = ProcessUtils.onlyPositiveNumber(searchData.getPage(),1);
        int limit = ProcessUtils.onlyPositiveNumber(searchData.getLimit(),20);
        int offset = (page -1 ) * limit;

        QArtist artist =QArtist.artist;

        BooleanBuilder andBuilder = new BooleanBuilder();


        List<ArtistResponseDto> items = jpaQueryFactory
                .selectFrom(artist)
                .leftJoin(artist.agency)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch()
                .stream()
                .map(ArtistResponseDto::new)
                .toList();


        Pageable pageable = PageRequest.of(page -1 , limit);
        Page<Artist> data = artistRepository.findAll(pageable);


        Pagination pagination = new Pagination(page , (int) data.getTotalElements(),10,limit,request);
        ListData<ArtistResponseDto> listData = new ListData<>();
        listData.setContent(items);
        listData.setPagination(pagination);

        return listData;
    }
}
