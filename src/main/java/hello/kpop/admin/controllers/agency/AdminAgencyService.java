package hello.kpop.admin.controllers.agency;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.agency.Agency;
import hello.kpop.agency.QAgency;
import hello.kpop.agency.dto.AgencyResponseDto;
import hello.kpop.agency.repository.AgencyRepository;
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
public class AdminAgencyService {

    private final HttpServletRequest request;
    private final AgencyRepository agencyRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public ListData<AgencyResponseDto> getList(SearchData searchData){
        int page = ProcessUtils.onlyPositiveNumber(searchData.getPage(),1);
        int limit = ProcessUtils.onlyPositiveNumber(searchData.getLimit(), 20);
        int offset = (page -1)*limit;

        QAgency agency = QAgency.agency;

        BooleanBuilder andBuilder = new BooleanBuilder();

        List<AgencyResponseDto> items = jpaQueryFactory
                .selectFrom(agency)
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch()
                .stream()
                .map(AgencyResponseDto::new)
                .toList();

        Pageable pageable = PageRequest.of(page -1 , limit);
        Page<Agency> data = agencyRepository.findAll(andBuilder,pageable);

        Pagination pagination = new Pagination(page,(int) data.getTotalElements(),10,limit,request);
        ListData<AgencyResponseDto> listData  =new ListData<>();
        listData.setContent(items);
        listData.setPagination(pagination);
        return listData;

    }


}
