package hello.kpop.board.service;


import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.board.Board;
import hello.kpop.board.QBoard;
import hello.kpop.board.dto.BoardListData;
import hello.kpop.board.dto.BoardSearchData;
import hello.kpop.board.dto.BoardViewData;
import hello.kpop.board.exception.BoardDataNotFoundException;
import hello.kpop.board.exception.BoardNotFoundException;
import hello.kpop.board.exception.ForbiddenBoard;
import hello.kpop.board.repository.BoardRepository;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.Pagination;
import hello.kpop.socialing.common.ProcessUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardInfoService {

    private final BoardRepository boardRepository;
    private final HttpServletRequest request;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory jpaQueryFactory;



    // 게시판 하나 조회
    public BoardViewData findBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if(board.isNotice_yn()){
            throw new ForbiddenBoard();
        }
        if(board.isDel_yn()){
            throw new BoardDataNotFoundException();
        }
        BoardViewData boardData = modelMapper.map(board, BoardViewData.class);
        boardData.setNickName(board.getUser().getNickname());
        return boardData;
    }

    public ListData<BoardListData> getList(BoardSearchData searchData){
        int page = ProcessUtils.onlyPositiveNumber(searchData.getPage() ,1);
        int limit = ProcessUtils.onlyPositiveNumber(searchData.getLimit(),20);
        int offset = (page -1) * limit;

        QBoard board = QBoard.board;

        List<BoardListData> items = jpaQueryFactory
                .selectFrom(board)
                .leftJoin(board.user)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .fetch()
                .stream()
                .map(BoardListData::new)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page -1  , limit, Sort.by(desc("regDt")));
        Page<Board> data = boardRepository.findAll(pageable);
        Pagination pagination = new Pagination (page , (int) data.getTotalElements(), 10 ,limit,request);
        ListData<BoardListData> listdata = new ListData<>();

        listdata.setPagination(pagination);
        listdata.setContent(items);

        filterList(listdata);
        return listdata;
    }

    //노출여부에 따른 목록데이터 필터
    public void filterList (ListData<BoardListData> dataList ){
        List<BoardListData> content = dataList.getContent();
        List<BoardListData> filteredList = content.stream()
                .filter(data -> !data.isNotice_yn())
                .filter(data -> !data.isDel_yn())
                .collect(Collectors.toList());
        dataList.setContent(filteredList);
    }




}
