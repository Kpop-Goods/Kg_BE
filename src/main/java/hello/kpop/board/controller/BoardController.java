package hello.kpop.board.controller;


import hello.kpop.board.dto.BoardListResponseDto;
import hello.kpop.board.dto.BoardRequestDto;
import hello.kpop.board.dto.BoardResponseDto;
import hello.kpop.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 등록
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        BoardResponseDto createdBoard = boardService.createBoard(requestDto);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    // 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardListResponseDto>> getAllBoards() {
        List<BoardListResponseDto> boards = boardService.findAllBoard();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 게시글 하나 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getOneBoard(@PathVariable Long id) {
        BoardResponseDto foundBoard = boardService.findBoard(id);
        return foundBoard != null ? new ResponseEntity<>(foundBoard, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        BoardResponseDto updatedBoardId = boardService.updateBoard(id, requestDto);
        return new ResponseEntity<>(updatedBoardId, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
