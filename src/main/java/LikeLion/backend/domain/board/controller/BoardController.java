package LikeLion.backend.domain.board.controller;

import LikeLion.backend.domain.auth.security.userDetails.CustomUserDetailsImpl;
import LikeLion.backend.domain.board.domain.entity.Board;
import LikeLion.backend.domain.board.domain.request.BoardCreateRequest;
import LikeLion.backend.domain.board.domain.response.BoardInfoResponse;
import LikeLion.backend.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final  BoardService boardService;

    // create board rest api
    @PostMapping("/boards")
    public BoardInfoResponse createBoard(@AuthenticationPrincipal CustomUserDetailsImpl userDetails, @RequestBody BoardCreateRequest boardCreateRequest) {
        return boardService.createBoard(userDetails.getUsername(), boardCreateRequest);
    }

//    // list all boards
//    @GetMapping("/boards")
//    public List<Board> listAllBoards() {
//        return boardService.listAllBoards();
//    }
//
//    // get board by id
//    @GetMapping("/boards/{id}")
//    public ResponseEntity<Board> getBoardById(@PathVariable Integer id) {
//        return boardService.getBoardById(id);
//    }
//
//    // update board
//    @PutMapping("/boards/{id}")
//    public ResponseEntity<Board> updateBoard(@PathVariable Integer id, @RequestBody Board boardDetails) {
//        return boardService.updateBoard(id, boardDetails);
//    }
//
//    // delete board
//    @DeleteMapping("/boards/{id}")
//    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
//        return boardService.deleteBoard(id);
//    }
}
