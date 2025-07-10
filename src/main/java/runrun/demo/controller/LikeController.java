package runrun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.Post;
import runrun.demo.model.User;
import runrun.demo.repository.PostRepository;
import runrun.demo.repository.UserRepository;
import runrun.demo.service.LikeService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId,
                                             @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String username = jwtUtil.getUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        boolean liked = likeService.toggleLike(user, post);

        return liked ? ResponseEntity.ok("좋아요 등록") : ResponseEntity.ok("좋아요 취소");

    }

    @GetMapping("/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {

        Post post = postRepository.findById(postId).orElseThrow();
        long count = likeService.countLikes(post);
        return ResponseEntity.ok(count);
    }
}
