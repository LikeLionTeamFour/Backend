package LikeLion.backend.domain.post.service;

import LikeLion.backend.domain.post.domain.entity.Post;
import LikeLion.backend.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // create board rest api
    public Post createBoard(@RequestBody Post board) {
        return postRepository.save(board);
    }
}
