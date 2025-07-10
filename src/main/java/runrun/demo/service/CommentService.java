package runrun.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import runrun.demo.dto.CommentRequestDto;
import runrun.demo.exception.CustomException;
import runrun.demo.model.Comment;
import runrun.demo.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {


    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Long postId, String content, String writer) {
        return commentRepository.save(new Comment(postId, content, writer));
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto dto, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("댓글을 찾을 수 없습니다."
                , HttpStatus.NOT_FOUND));
        if (!comment.getWriter().equals(username)) {
            throw new CustomException("수정 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new CustomException("댓글을 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED));

        if (!comment.getWriter().equals(username)) {
            throw new CustomException("삭제 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
        }

        commentRepository.delete(comment);

    }

    public List<Comment> searchCommentsByKeyword(String keyword) {
        return commentRepository.findByContentContaining(keyword);
    }



}
