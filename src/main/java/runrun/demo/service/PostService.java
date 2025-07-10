package runrun.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import runrun.demo.dto.PostRequestDto;
import runrun.demo.dto.PostResponseDto;
import runrun.demo.exception.CustomException;
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

    @Transactional
    public void updatePost(Long postId, PostRequestDto request, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!post.getWriter().equals(username)) {
            throw new CustomException("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
    }

    @Transactional
    public void deletePost(Long postId,String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

        if (!post.getWriter().equals(username)) {
            throw new CustomException("작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        postRepository.delete(post);

    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingOrWriterContainingOrContentContaining(keyword, keyword, keyword);
    }

    public Page<Post> getPostPage(Pageable pageable) {

        return postRepository.findAll(pageable);
    }



}
