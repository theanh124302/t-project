package tproject.postservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tproject.postservice.dto.request.CreatePostRequest;
import tproject.postservice.dto.response.CreatedPostResponse;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.Visibility;
import tproject.postservice.repository.PostRepository;

import java.util.Iterator;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public CreatedPostResponse createPost(CreatePostRequest request, Long userId) {

        PostEntity postEntity = PostEntity.builder()
                .userId(userId)
                .status("active")
                .hidden(false)
                .content(request.getContent())
                .visibility(Visibility.PUBLIC)
                .build();

        PostEntity savedPost = postRepository.save(postEntity);

        Iterator<CreatePostRequest.Media> mediaIterator = request.getPostMedia().

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
