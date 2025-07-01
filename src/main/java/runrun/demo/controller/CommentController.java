package runrun.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import runrun.demo.model.Comment;
import runrun.demo.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> writeComment(@PathVariable Long postId,  // {postId} 변수 주입
                                                @RequestBody Map<String, String> request,
                                                Authentication authentication) {
        String content = request.get("content"); // 댓글 내용 key : value
        String writer = authentication.getName();  // username

        Comment comment = commentService.saveComment(postId, content, writer);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));  // 응답 + HTTP 상태코드 리턴
    }
}
