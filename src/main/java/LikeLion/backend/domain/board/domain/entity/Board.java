package LikeLion.backend.domain.board.domain.entity;

import LikeLion.backend.domain.auth.domain.entity.User;
import LikeLion.backend.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view")
    private Long view;

    @Column(name = "like")
    private Long like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;




    @Builder
    public Board(String title, String content, User user,Long viewCount,Long likeCount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.view = viewCount;
        this.like = likeCount;
    }
}
