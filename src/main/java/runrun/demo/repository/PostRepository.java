package runrun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import runrun.demo.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingOrWriterContainingOrContentContaining(String title, String writer, String content);


}
