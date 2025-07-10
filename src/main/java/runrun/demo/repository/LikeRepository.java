package runrun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import runrun.demo.model.Like;
import runrun.demo.model.Post;
import runrun.demo.model.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // ✅ 특정 사용자와 게시글 조합으로 좋아요 여부 확인
    Optional<Like> findByUserAndPost(User user, Post post);

    // ✅ 게시글의 좋아요 개수 조회
    Long countByPost(Post post);

    // ✅ 게시글 삭제 시 좋아요도 함께 삭제하기 위함
    void deleteAllByPost(Post post);
}
