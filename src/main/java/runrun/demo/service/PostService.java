package runrun.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import runrun.demo.dto.PostResponseDto;
import runrun.demo.exception.CustomException;
import runrun.demo.model.Post;
import runrun.demo.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(String title, String content, String writer) {
        return postRepository.save(new Post(title, content, writer));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException
                ("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return new PostResponseDto(post);

    }
    public List<Post> getPostsByWriter(String writer) {
        return postRepository.findAll().stream()
                .filter(p -> p.getWriter().equals(writer))
                .toList();
    }
}
