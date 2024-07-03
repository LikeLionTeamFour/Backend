package LikeLion.backend.domain.board.repository;


import LikeLion.backend.domain.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> { }
