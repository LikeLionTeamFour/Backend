package LikeLion.backend.domain.board.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponse {
    private Long boardId;
    private String title;
    private String writer;
    private Long view;
    private Long like;
    private LocalDateTime createdAt;

    // Getters and setters
}
