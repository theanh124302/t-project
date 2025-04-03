package tproject.postwservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tproject.postwservice.dto.Request.CreatePostRequest;
import tproject.postwservice.dto.Response.CreatePostResponse;
import tproject.postwservice.entity.PostContentEntity;
import tproject.postwservice.entity.PostEntity;
import tproject.postwservice.enumeration.Visibility;
import tproject.postwservice.repository.PostContentRepository;
import tproject.postwservice.repository.PostRepository;
import tproject.postwservice.service.PostService;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

     private final PostRepository postRepository;
     private final PostContentRepository postContentRepository;

     @Override
     public Mono<CreatePostResponse> createPost(CreatePostRequest createPostRequest, Long actorId) {
         PostEntity postEntity = PostEntity.builder()
                 .userId(actorId)
                 .status("DRAFT")
                 .visibility(Visibility.PUBLIC)
                 .build();

         Mono<PostEntity> savedPost = postRepository.save(postEntity);
         return savedPost.flatMap(post -> {
             PostContentEntity postContentEntity = PostContentEntity.builder()
                     .postId(post.getId())
                     .content(createPostRequest.getPostContent().getContent())
                     .build();

             return postContentRepository.save(postContentEntity)
                     .map(content -> CreatePostResponse.builder()
                             .id(post.getId())
                             .userId(post.getUserId())
                             .status(post.getStatus())
                             .visibility(post.getVisibility().name())
                             .createdAt(post.getCreatedAt())
                             .build());
         });

     }

}
