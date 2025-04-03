package tproject.postwservice.service;

import reactor.core.publisher.Mono;
import tproject.postwservice.dto.Request.CreatePostRequest;
import tproject.postwservice.dto.Response.CreatePostResponse;

public interface PostService {

    Mono<CreatePostResponse> createPost(CreatePostRequest createPostRequest, Long actorId);

}
