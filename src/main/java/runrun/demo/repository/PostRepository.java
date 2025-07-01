package runrun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import runrun.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {}
