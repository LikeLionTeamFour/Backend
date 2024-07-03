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
    private Integer view;
    private Integer like;
    private LocalDateTime createdAt;

    public BoardInfoResponse(Long boardId, String title, String content, String writer, Integer view, Integer like, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.like = like;
        this.createdAt = createdAt;
    }

    public static BoardInfoResponse from(Board board , String writer , Integer view , Integer like){
        return new BoardInfoResponse(board.getId(), board.getTitle(), board.getContent(), writer, view, like, board.getCreatedAt());
    }
}
