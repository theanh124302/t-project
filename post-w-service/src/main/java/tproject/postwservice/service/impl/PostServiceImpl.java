package tproject.postwservice.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
     private final PostContentRepository postContentRepository;
    @Override
    public Mono<CreatePostResponse> createPost(CreatePostRequest createPostRequest, Long actorId) {
        log.info("Creating post with request: {}", createPostRequest);
        log.info("Initial thread: {}", Thread.currentThread().getName());

        PostEntity postEntity = PostEntity.builder()
                .userId(actorId)
                .status("DRAFT")
                .visibility(Visibility.PUBLIC)
                .build();

        // Thêm các toán tử debug cho postRepository.save
        Mono<PostEntity> savedPost = postRepository.save(postEntity)
                .doOnSubscribe(s -> log.info("PostRepository.save - OnSubscribe thread: {}", Thread.currentThread().getName()))
                .doOnRequest(n -> log.info("PostRepository.save - OnRequest thread: {}", Thread.currentThread().getName()))
                .doOnSuccess(p -> log.info("PostRepository.save - OnSuccess thread: {}", Thread.currentThread().getName()))
                .doOnError(e -> log.error("PostRepository.save - OnError thread: {}", Thread.currentThread().getName()));

        log.info("After savedPost declaration, thread: {}", Thread.currentThread().getName());

        return savedPost.flatMap(post -> {
                    log.info("FlatMap operator thread (post saved): {}", Thread.currentThread().getName());

                    PostContentEntity postContentEntity = PostContentEntity.builder()
                            .postId(post.getId())
                            .content(createPostRequest.getPostContent().getContent())
                            .build();

                    log.info("Before postContentRepository.save, thread: {}", Thread.currentThread().getName());

                    // Thêm các toán tử debug cho postContentRepository.save
                    return postContentRepository.save(postContentEntity)
                            .doOnSubscribe(s -> log.info("ContentRepository.save - OnSubscribe thread: {}", Thread.currentThread().getName()))
                            .doOnRequest(n -> log.info("ContentRepository.save - OnRequest thread: {}", Thread.currentThread().getName()))
                            .doOnSuccess(c -> log.info("ContentRepository.save - OnSuccess thread: {}", Thread.currentThread().getName()))
                            .map(content -> {
                                log.info("Map operator thread (content saved): {}", Thread.currentThread().getName());

                                CreatePostResponse response = CreatePostResponse.builder()
                                        .id(post.getId())
                                        .userId(post.getUserId())
                                        .status(post.getStatus())
                                        .visibility(post.getVisibility().name())
                                        .createdAt(post.getCreatedAt())
                                        .build();

                                log.info("Response creation thread: {}", Thread.currentThread().getName());
                                return response;
                            });
                })
                .doOnSuccess(response -> log.info("Final operation complete on thread: {}", Thread.currentThread().getName()))
                .doFinally(signalType -> log.info("doFinally signal: {} on thread: {}", signalType, Thread.currentThread().getName()));
    }

}
