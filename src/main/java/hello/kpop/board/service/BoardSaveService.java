package hello.kpop.board.service;


import hello.kpop.board.Board;
import hello.kpop.board.dto.BoardData;
import hello.kpop.board.exception.BoardNotFoundException;
import hello.kpop.board.repository.BoardRepository;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.UserAuthentication;
import hello.kpop.socialing.common.exception.UnAuthorizedException;
import hello.kpop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardSaveService {

    private final BoardRepository boardRepository;
    private final UserAuthentication userAuthentication;
    private final ProcessUtils utils;


    //공지사항 등록
    public void createBoard(BoardData data , Errors errors , Authentication authentication) {
        if(errors.hasErrors()){
            return;
        }
        saveBoard(data , authentication);
    }


    // 게시글 수정
    public void updateBoard(Long id, BoardData data ,Errors errors, Authentication authentication){

        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        // 관리자 체크 -- 권한이 관리자면 모든 공지 게시판 수정및 삭제 가능
        if (!userAuthentication.checkAdmin(authentication)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("UnAuthorized.Admin","errors"));
        }
        if(errors.hasErrors()){
            return ;
        }
        board.modifyBoardInfo(data);
    }


    //등록 메서드
    public void saveBoard(BoardData data ,Authentication authentication){

        // 관리자 체크
        if (!userAuthentication.checkAdmin(authentication)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("UnAuthorized.Admin","errors"));
        }
        User user = userAuthentication.getUser(authentication);

        Board board = Board.builder()
                .user(user)
                .subject(data.getSubject())
                .content(data.getContent())
                .notice_yn(data.isNotice_yn())
                .del_yn(data.isDel_yn())
                .build();
        boardRepository.saveAndFlush(board);

        data.setCreatedBy(board.getCreatedBy());
        data.setRegDt(board.getRegDt());
    }

//    public void update(List<Integer> idxes){
//        if(idxes == null || idxes.isEmpty()){
//            throw new AlertException("수정할 게시판을 선택하세요");
//        }
//        for(int idx : idxes){
//            String bId = utils.getParam("sub_"+idx);
//            Board board = boardRepository.findByBId(bId);
//            if(board == null) continue;




}
