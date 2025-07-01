package runrun.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import runrun.demo.model.Post;
import runrun.demo.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 등록 (JWT 인증된 사용자만 가능)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Map<String, String> request,
                                           Authentication authentication) {
        String title = request.get("title");
        String content = request.get("content");
        String writer = authentication.getName(); // 토큰에서 추출된 사용자명

        Post post = postService.savePost(title, content, writer);
        return ResponseEntity.ok(post);
    }

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 내 글만 조회
    @GetMapping("/my")
    public ResponseEntity<List<Post>> getMyPosts(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.getPostsByWriter(username));
    }
}

