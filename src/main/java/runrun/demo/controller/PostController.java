package runrun.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import runrun.demo.dto.PostRequestDto;
import runrun.demo.dto.PostResponseDto;
import runrun.demo.dto.ResponseDto;
import runrun.demo.exception.NotFoundException;
import runrun.demo.exception.UnauthorizedException;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.Post;
import runrun.demo.repository.PostRepository;
import runrun.demo.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final JwtUtil jwtUtil;
    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(JwtUtil jwtUtil, PostService postService, PostRepository postRepository) {
        this.jwtUtil = jwtUtil;
        this.postService = postService;
        this.postRepository = postRepository;
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

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPost(@PathVariable Long id) {
        PostResponseDto dto = postService.getPostById(id);
        return ResponseEntity.ok(ResponseDto.success("게시글 조회 성공", dto));
    }
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto request,
            @RequestHeader("Authorization") String token
    ) {

        // 1. 토큰에서 사용자 이름 추출 (Bearer 제거 포함)
        String username = jwtUtil.getUsername(token.replace("Bearer ", ""));

        postService.updatePost(id, request, username);

        return ResponseEntity.ok("게시글 수정 완료");
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        String username = jwtUtil.getUsername(token.replace("Bearer ", ""));

        postService.deletePost(id, username);

        return ResponseEntity.ok("게시글 삭제 완료");
    }

}

