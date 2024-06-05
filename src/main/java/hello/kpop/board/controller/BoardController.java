package hello.kpop.board.controller;


import hello.kpop.board.dto.BoardData;
import hello.kpop.board.dto.BoardListData;
import hello.kpop.board.dto.BoardSearchData;
import hello.kpop.board.dto.BoardViewData;
import hello.kpop.board.service.BoardDeleteService;
import hello.kpop.board.service.BoardInfoService;
import hello.kpop.board.service.BoardSaveService;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ResponseJSONData;
import hello.kpop.socialing.common.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardInfoService infoService;
    private final BoardDeleteService deleteService;
    private final BoardSaveService saveService;

    // 게시글 등록
    @PostMapping
    public ResponseJSONData<BoardData> createBoard(@RequestBody @Valid BoardData data, Errors errors , Authentication authentication) {
        saveService.createBoard(data, errors ,authentication);
        errorProcess(errors);
        return new ResponseJSONData<>(data,HttpStatus.CREATED,"공지사항 등록 완료");
    }

    // 전체 목록 조회
    @GetMapping("/list")
    public ListData<BoardListData> getBoardList(BoardSearchData data) {
        ListData<BoardListData> boardList = infoService.filterList(data);
        return new ListData<>(boardList.getContent() , boardList.getPagination());
    }

    // 게시글 하나 조회
    @GetMapping("/view/{id}")
    public ResponseJSONData<BoardViewData> viewBoard(@PathVariable Long id) {
        BoardViewData data = infoService.findBoard(id);
        return new ResponseJSONData<>(data , "공지사항 상세 조회 성공");
    }


    //게시글 수정
    @PutMapping("/{id}")
    public ResponseJSONData<?> updateBoard(@PathVariable Long id, @RequestBody @Valid BoardData data, Errors errors , Authentication authentication) {
        saveService.updateBoard(id, data, errors, authentication);
        errorProcess(errors);
        return new ResponseJSONData<>(data,"수정이완료 되었씁니다.");
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseJSONData<?> deleteBoard(@PathVariable Long id , Authentication authentication) {
        deleteService.deleteBoard(id,authentication);
        return new ResponseJSONData<>(null , "공지사항이 삭제 되었습니다.");
    }

    private void errorProcess(Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
    }
}
