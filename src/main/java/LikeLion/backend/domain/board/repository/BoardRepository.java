package LikeLion.backend.domain.board.repository;


import LikeLion.backend.domain.board.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Post, Integer> { }
