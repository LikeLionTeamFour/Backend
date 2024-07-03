package LikeLion.backend.domain.board.service;

import LikeLion.backend.domain.board.domain.entity.Post;
import LikeLion.backend.domain.board.repository.BoardRepository;
import LikeLion.backend.global.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // create board rest api
    public Post createBoard(Post board) {
        return boardRepository.save(board);
    }

    // list all boards
    public List<Post> listAllBoards() {
        return boardRepository.findAll();
    }

    // get board by id
    public ResponseEntity<Post> getBoardById(Integer id) {
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        int cnt = post.getViewCnt();
        post.setViewCnt(cnt + 1);
        boardRepository.save(post); // 조회 수 증가 후 저장

        return ResponseEntity.ok(post);
    }

    // update board
    public ResponseEntity<Post> updateBoard(Integer id, Post boardDetails) {
        Post board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());

        Post updatedBoard = boardRepository.save(board);
        return ResponseEntity.ok(updatedBoard);
    }

    // delete board
    public ResponseEntity<Map<String, Boolean>> deleteBoard(Integer id) {
        Post board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        boardRepository.delete(board);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
