package runrun.demo.service;

import org.springframework.stereotype.Service;
import runrun.demo.model.Post;
import runrun.demo.repository.PostRepository;

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

    public List<Post> getPostsByWriter(String writer) {
        return postRepository.findAll().stream()
                .filter(p -> p.getWriter().equals(writer))
                .toList();
    }
}
