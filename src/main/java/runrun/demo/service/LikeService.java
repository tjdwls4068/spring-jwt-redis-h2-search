package runrun.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import runrun.demo.model.Like;
import runrun.demo.model.Post;
import runrun.demo.model.User;
import runrun.demo.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;


    // 좋아요가 된 상태면 취소, 안되어 있는 상태면 좋아요 기능
    @Transactional
    public boolean toggleLike(User user, Post post) {
        return likeRepository.findByUserAndPost(user,post).map(like -> {
            likeRepository.delete(like);
            return false;
        } ).orElseGet(() -> {
            likeRepository.save(new Like(user, post));
            return true;
        });
    }

    // 게시글 좋아요 수 반환
    public long countLikes(Post post) {
        return likeRepository.countByPost(post);
    }
}
