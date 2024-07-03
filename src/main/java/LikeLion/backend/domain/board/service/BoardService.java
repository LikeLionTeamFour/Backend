package LikeLion.backend.domain.board.service;

import LikeLion.backend.domain.auth.domain.entity.User;
import LikeLion.backend.domain.auth.repository.UserRepository;
import LikeLion.backend.domain.board.domain.entity.Board;
import LikeLion.backend.domain.board.domain.request.BoardCreateRequest;
import LikeLion.backend.domain.board.domain.response.BoardInfoResponse;
import LikeLion.backend.domain.board.repository.BoardRepository;
import LikeLion.backend.global.exception.ErrorCode;
import LikeLion.backend.global.exception.GlobalExceptionHandler;
import LikeLion.backend.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Transactional
    public BoardInfoResponse createBoard(String username, BoardCreateRequest boardCreateRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.ACCESS_DENIED));

        Board board = Board.builder()
                .title(boardCreateRequest.getTitle())
                .content(boardCreateRequest.getContent())
                .user(user)
                .build();
        user.getBoards().add(board);

        boardRepository.save(board);
        return BoardInfoResponse.from(board,username,0,0);
    }

    // list all boards
    public List<Board> listAllBoards() {
        return boardRepository.findAll();
    }

    // get board by id
    public ResponseEntity<Board> getBoardById(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        int cnt = board.getView();
        board.setView(cnt + 1);
        boardRepository.save(board); // 조회 수 증가 후 저장

        return ResponseEntity.ok(board);
    }

    // update board
    public ResponseEntity<Board> updateBoard(Integer id, Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());

        Board updatedBoard = boardRepository.save(board);
        return ResponseEntity.ok(updatedBoard);
    }

    // delete board
    public ResponseEntity<Map<String, Boolean>> deleteBoard(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        boardRepository.delete(board);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
