package LikeLion.backend.domain.board.controller;

import LikeLion.backend.domain.board.domain.entity.Post;
import LikeLion.backend.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // create board rest api
    @PostMapping("/boards")
    public Post createBoard(@RequestBody Post post) {
        return boardService.createBoard(post);
    }

    // list all boards
    @GetMapping("/boards")
    public List<Post> listAllBoards() {
        return boardService.listAllBoards();
    }

    // get board by id
    @GetMapping("/boards/{id}")
    public ResponseEntity<Post> getBoardById(@PathVariable Integer id) {
        return boardService.getBoardById(id);
    }

    // update board
    @PutMapping("/boards/{id}")
    public ResponseEntity<Post> updateBoard(@PathVariable Integer id, @RequestBody Post boardDetails) {
        return boardService.updateBoard(id, boardDetails);
    }

    // delete board
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        return boardService.deleteBoard(id);
    }
}
