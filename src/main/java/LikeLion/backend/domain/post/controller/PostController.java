package LikeLion.backend.domain.post.controller;

import LikeLion.backend.domain.post.domain.entity.Post;
import LikeLion.backend.domain.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    // create board rest api
    @PostMapping("/boards")
    public Post createBoard(@RequestBody Post post) {
        return postService.createBoard(post);
    }

    // list all boards
    @GetMapping("/boards")
    public List<Post> listAllBoards() {
        return postService.listAllBoards();
    }

    // get board by id
    @GetMapping("/boards/{id}")
    public ResponseEntity<Post> getBoardById(@PathVariable Integer id) {
        return postService.getBoardById(id);
    }

    // update board
    @PutMapping("/boards/{id}")
    public ResponseEntity<Post> updateBoard(@PathVariable Integer id, @RequestBody Post boardDetails) {
        return postService.updateBoard(id, boardDetails);
    }

    // delete board
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable Integer id) {
        return postService.deleteBoard(id);
    }
}
