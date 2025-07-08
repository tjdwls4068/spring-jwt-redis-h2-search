package runrun.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import runrun.demo.dto.CommentRequestDto;
import runrun.demo.dto.ResponseDto;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.Comment;
import runrun.demo.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    public CommentController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
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

    @PutMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<ResponseDto<String>> updateComment(@PathVariable Long commentId,
                                                             @RequestBody CommentRequestDto dto,
                                                             @RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsername(token.substring(7));
        commentService.updateComment(commentId, dto, username);
        return ResponseEntity.ok(ResponseDto.success("댓글 수정 완료", null));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<ResponseDto<String>> deleteComment(@PathVariable Long commentId,
                                                             @RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsername(token.substring(7));
        commentService.deleteComment(commentId, username);

        return ResponseEntity.ok(ResponseDto.success("댓글 삭제 완료", null));

    }

}
