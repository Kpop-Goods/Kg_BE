package hello.kpop.board.service;


import hello.kpop.board.dto.BoardListResponseDto;
import hello.kpop.board.dto.BoardRequestDto;
import hello.kpop.board.dto.BoardResponseDto;
import hello.kpop.board.dto.NoticeBoard;
import hello.kpop.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    // 게시판 생성
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        NoticeBoard board = new NoticeBoard(boardRequestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    // 게시판 전체 조회
    public List<BoardListResponseDto> findAllBoard() {
        try{
            List<NoticeBoard> boardList = boardRepository.findAll();

            List<BoardListResponseDto> responseDtoList = new ArrayList<>();

            for (NoticeBoard board : boardList) {
                responseDtoList.add(new BoardListResponseDto(board));
            }
            return responseDtoList;
        } catch (Exception e) {
//
        }
        return null;
    }

    // 게시판 하나 조회
    public BoardResponseDto findBoard(Long id) {
        NoticeBoard board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회 실패")
        );
        return new BoardResponseDto(board);
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto){
        NoticeBoard board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("아이디값이 없습니다")
        );

        board.modifyBoardInfo(requestDto);
        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);

    }
}