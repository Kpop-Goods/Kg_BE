package hello.kpop.board.service;

import hello.kpop.board.Board;
import hello.kpop.board.exception.BoardDataNotFoundException;
import hello.kpop.board.exception.BoardNotFoundException;
import hello.kpop.board.repository.BoardRepository;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.UserAuthentication;
import hello.kpop.socialing.common.exception.UnAuthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;
    private final UserAuthentication userAuthentication;

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long id , Authentication authentication) {

        //공지사항이 없으면 예외
        Board board =boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        // 관리자 권한 확인
        if (!userAuthentication.checkAdmin(authentication)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("UnAuthorized.Admin","errors"));
        }
        // 공지사항 데이터가 없으면 예외
        if(board.isDel_yn()){
            throw new BoardDataNotFoundException();
        }

        //삭제여부를 변경하고 저장
        board.deleteStatus();
        boardRepository.save(board);

    }
}
