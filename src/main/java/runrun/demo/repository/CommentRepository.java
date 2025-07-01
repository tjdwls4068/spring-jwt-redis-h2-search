package runrun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import runrun.demo.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByWriter(String writer);
}
