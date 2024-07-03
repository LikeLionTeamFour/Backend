package LikeLion.backend.domain.board.service;

import LikeLion.backend.domain.auth.domain.entity.User;
import LikeLion.backend.domain.auth.repository.UserRepository;
import LikeLion.backend.domain.board.domain.entity.Board;
import LikeLion.backend.domain.board.domain.request.BoardInfoRequest;
import LikeLion.backend.domain.board.domain.response.BoardInfoResponse;
import LikeLion.backend.domain.board.domain.response.BoardResponse;
import LikeLion.backend.domain.board.repository.BoardRepository;
import LikeLion.backend.global.exception.ErrorCode;
import LikeLion.backend.global.exception.GlobalExceptionHandler;
import LikeLion.backend.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Transactional
    public BoardInfoResponse createBoard(String username, BoardInfoRequest boardInfoRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.ACCESS_DENIED));

        Board board = Board.builder()
                .title(boardInfoRequest.getTitle())
                .content(boardInfoRequest.getContent())
                .user(user)
                .viewCount(0L)
                .likeCount(0L)
                .build();
        user.getBoards().add(board);

        boardRepository.save(board);
        return BoardInfoResponse.from(board,username,board.getView(),board.getLike());
    }

    @Transactional(readOnly = true)
    public List<Board> listAllBoards() {
        return boardRepository.findAll();
    }


    @Transactional
    public BoardInfoResponse getBoardById( Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));
        board.setView(board.getView()+1);
        return BoardInfoResponse.from(board,board.getUser().getUsername(),board.getView(),board.getLike());
    }

    @Transactional
    public void increaseLike(String username, Long boardId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.ACCESS_DENIED));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));
        board.setLike(board.getLike()+1);
    }




    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Board not exist with id :" + id));

        boardRepository.delete(board);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @Transactional
    public BoardInfoResponse updateBoard(String username, BoardInfoRequest boardInfoRequest,Long boardId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.ACCESS_DENIED));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));
        if(!board.getUser().getUsername().equals(username)){
            throw new ServiceException(ErrorCode.ACCESS_DENIED);
        }
        board.setTitle(boardInfoRequest.getTitle());
        board.setContent(boardInfoRequest.getContent());
        return BoardInfoResponse.from(board,username,board.getView(),board.getLike());
    }

    @Transactional
    public void deleteBoard(String username , Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOARD_NOT_FOUND));
        userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.ACCESS_DENIED));
        if(!board.getUser().getUsername().equals(username)){
            throw new ServiceException(ErrorCode.ACCESS_DENIED);
        }
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getBoards(Pageable pageable) {
        Page<Board> boardPage= boardRepository.findAll(pageable);
        List<BoardResponse> content = boardPage.getContent().stream().map(board -> {
            BoardResponse response = new BoardResponse();
            response.setBoardId(board.getId());
            response.setTitle(board.getTitle());
            response.setWriter(board.getUser().getUsername());
            response.setView(board.getView());
            response.setLike(board.getLike());
            response.setCreatedAt(board.getCreatedAt());
            return response;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", content);
        response.put("pageable", new HashMap<String, Object>() {{
            put("page", boardPage.getNumber());
            put("size", boardPage.getSize());
            put("totalPages", boardPage.getTotalPages());
            put("totalElements", boardPage.getTotalElements());
            put("numberOfElements", boardPage.getNumberOfElements());
            put("first", boardPage.isFirst());
            put("last", boardPage.isLast());
        }});
        return response;
    }
}
