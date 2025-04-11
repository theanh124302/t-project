package tproject.postservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tproject.postservice.dto.request.CreatePostRequest;
import tproject.postservice.dto.response.CreatedPostResponse;
import tproject.postservice.entity.MediaEntity;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.Visibility;
import tproject.postservice.repository.MediaRepository;
import tproject.postservice.repository.PostRepository;

import java.util.Iterator;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MediaRepository mediaRepository;

    @Transactional
    public CreatedPostResponse createPost(CreatePostRequest request, Long userId) {

        PostEntity postEntity = PostEntity.builder()
                .userId(userId)
                .status("active")
                .hidden(false)
                .content(request.getContent())
                .visibility(Visibility.PUBLIC)
                .build();

        PostEntity savedPost = postRepository.save(postEntity);

        MediaEntity media = MediaEntity.builder()
                .postId(savedPost.getId())
                .mediaType(request.getPostMedia().getMediaType())
                .mediaUrl(request.getPostMedia().getMediaUrl())
                .build();

        MediaEntity savedMedia = mediaRepository.save(media);

        return CreatedPostResponse.builder()
                .postId(savedPost.getId())
                .content(savedPost.getContent())
                .mediaUrl(savedMedia.getMediaUrl())
                .build();

    }
}
