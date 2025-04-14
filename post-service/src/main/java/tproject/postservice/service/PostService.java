package tproject.postservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tproject.postservice.dto.request.CreatePostRequestDTO;
import tproject.postservice.dto.response.CreatedPostResponseDTO;
import tproject.postservice.entity.FileEntity;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.PostStatus;
import tproject.postservice.repository.MediaRepository;
import tproject.postservice.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MediaRepository mediaRepository;

    @Transactional
    public CreatedPostResponseDTO createLightPost(CreatePostRequestDTO request, Long userId, MultipartFile uploadMedia) {

        PostEntity postEntity = initPostEntity(request, userId);

        return CreatedPostResponseDTO.builder()
                .postId(postEntity.getId())
                .content(postEntity.getContent())
                .build();

    }

    private PostEntity initPostEntity(CreatePostRequestDTO request, Long userId) {

        PostEntity postEntity = PostEntity.builder()
                .userId(userId)
                .status(PostStatus.INITIAL)
                .hidden(false)
                .content(request.getContent())
                .visibility(request.getVisibility())
                .build();

        PostEntity savedPost = postRepository.save(postEntity);

        return savedPost;

    }
}
