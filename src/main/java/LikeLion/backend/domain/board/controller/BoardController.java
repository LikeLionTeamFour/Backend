package LikeLion.backend.domain.board.controller;

import LikeLion.backend.domain.auth.security.userDetails.CustomUserDetailsImpl;
import LikeLion.backend.domain.board.domain.entity.Board;
import LikeLion.backend.domain.board.domain.request.BoardInfoRequest;
import LikeLion.backend.domain.board.domain.response.BoardInfoResponse;
import LikeLion.backend.domain.board.service.AwsService;
import LikeLion.backend.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final  BoardService boardService;
    private final AwsService awsService;

    // create board rest api
    @PostMapping("/v1/board")
    public ResponseEntity<BoardInfoResponse> createBoard(@AuthenticationPrincipal CustomUserDetailsImpl userDetails, @RequestBody BoardInfoRequest boardInfoRequest) {
        return ResponseEntity.ok(boardService.createBoard(userDetails.getUsername(), boardInfoRequest));
    }

    @GetMapping("/v1/board/{boardId}")
    public ResponseEntity<BoardInfoResponse> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoardById(boardId));
    }

    @PostMapping("/v1/board/like/{boardId}")
    public ResponseEntity<Void> increaseLike(@AuthenticationPrincipal CustomUserDetailsImpl userDetails, @PathVariable Long boardId) {
        boardService.increaseLike(userDetails.getUsername(),boardId);
        return ResponseEntity.ok().build();
    }
    // list all boards
    @GetMapping("/v1/boards/listAll")
    public List<Board> listAllBoards() {
        return boardService.listAllBoards();
    }

    @PutMapping("/v1/board/{boardId}")
    public ResponseEntity<BoardInfoResponse> updateBoard(
            @AuthenticationPrincipal CustomUserDetailsImpl userDetails,
            @RequestBody BoardInfoRequest boardInfoRequest,
            @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.updateBoard(userDetails.getUsername(), boardInfoRequest,boardId));
    }

    @DeleteMapping("/v1/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@AuthenticationPrincipal CustomUserDetailsImpl userDetails, @PathVariable Long boardId) {
        boardService.deleteBoard(userDetails.getUsername(), boardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/board/list")
    public ResponseEntity<Map<String, Object>>  getBoards(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(boardService.getBoards(pageable));
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<String> getFile(@PathVariable(value = "filename") String fileName) throws IOException {

        String url = awsService.getPreSignedUrl(fileName);

        return new ResponseEntity<>(url, HttpStatus.OK);
    }

}
