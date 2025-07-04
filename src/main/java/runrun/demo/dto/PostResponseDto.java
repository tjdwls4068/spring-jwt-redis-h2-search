package runrun.demo.dto;

import lombok.Getter;
import runrun.demo.model.Post;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {


    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime localDateTime;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getTitle();
        this.localDateTime = post.getLocalDateTime();
    }
}
