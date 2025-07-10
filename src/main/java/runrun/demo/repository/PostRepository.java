package runrun.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import runrun.demo.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    Page<Post> findAll(Pageable pageable);

    List<Post> findByWriter(String writer);

    List<Post> findByTitleContainingOrWriterContainingOrContentContaining(String title, String writer, String content);


}
