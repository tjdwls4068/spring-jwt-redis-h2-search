package runrun.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
