package hello.kpop.admin.controllers.user;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.admin.commons.SearchData;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.Pagination;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.user.QUser;
import hello.kpop.user.User;
import hello.kpop.user.dto.UserResponseDto;
import hello.kpop.user.repository.UserRepository;
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
public class AdminUserService {

    private final UserRepository userRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final HttpServletRequest request;



    public ListData<UserResponseDto> getList(SearchData searchData){
        int page = ProcessUtils.onlyPositiveNumber(searchData.getPage(),1);
        int limit = ProcessUtils.onlyPositiveNumber(searchData.getLimit(),20);
        int offset = (page -1 ) * limit;

        QUser user = QUser.user;

        BooleanBuilder andBuilder = new BooleanBuilder();


        List<UserResponseDto> items = jpaQueryFactory
                .selectFrom(user)
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch()
                .stream()
                .map(UserResponseDto::new)
                .toList();


        Pageable pageable = PageRequest.of(page -1 , limit);
        Page<User> data = userRepository.findAll(pageable);


        Pagination pagination = new Pagination(page , (int) data.getTotalElements(),10,limit,request);
        ListData<UserResponseDto> listData = new ListData<>();
        listData.setContent(items);
        listData.setPagination(pagination);

        return listData;
    }





}
