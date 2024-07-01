package LikeLion.backend.domain.post.controller;

import LikeLion.backend.domain.post.domain.entity.Post;
import LikeLion.backend.domain.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    // create board rest api
    @PostMapping("/boards")
    public Post createBoard(@RequestBody Post post) {
        return postService.createBoard(post);
    }

    // list all boards

}
