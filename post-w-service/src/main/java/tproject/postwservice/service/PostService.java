package tproject.postwservice.service;

import tproject.postwservice.dto.Request.CreatePostRequest;
import tproject.postwservice.dto.Response.CreatePostResponse;

public interface PostService {

    CreatePostResponse createPost(CreatePostRequest createPostRequest, Long actorId);

}
