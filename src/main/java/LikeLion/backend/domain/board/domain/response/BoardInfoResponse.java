package LikeLion.backend.domain.board.domain.response;
import LikeLion.backend.domain.board.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardInfoResponse {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private Long view;
    private Long like;
    private LocalDateTime createdAt;

    public BoardInfoResponse(Long boardId, String title, String content, String writer, Long view, Long like, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.like = like;
        this.createdAt = createdAt;
    }

    public static BoardInfoResponse from(Board board , String writer , Long view , Long like){
        return new BoardInfoResponse(board.getId(), board.getTitle(), board.getContent(), writer, view, like, board.getCreatedAt());
    }
}
