package tproject.postservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tproject.postservice.dto.response.CreatedPostResponse;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.Visibility;
import tproject.postservice.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public CreatedPostResponse createPost(String content, Long userId) {
        // Create a new post entity
        PostEntity postEntity = PostEntity.builder()
                .userId(userId)
                .status("active")
                .hidden(false)
                .visibility(Visibility.PUBLIC)
                .build();

        // Save the post entity to the database
        PostEntity savedPost = postRepository.save(postEntity).block();

        // Create a new post content entity
        PostContentEntity postContentEntity = PostContentEntity.builder()
                .postId(savedPost.getId())
                .content(content)
                .build();

        // Save the post content entity to the database
        postContentRepository.save(postContentEntity).block();

        return CreatedPostResponse.builder()
                .postId(savedPost.getId())
                .content(content)
                .build();
    }

}
