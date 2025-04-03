package tproject.postwservice.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tproject.postwservice.dto.Request.CreatePostRequest;
import tproject.postwservice.dto.Response.CreatePostResponse;
import tproject.postwservice.service.PostService;

@RestController
@RequestMapping()
@AllArgsConstructor
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreatePostResponse> createPostPublic(@RequestBody CreatePostRequest request) {
        log.info("Creating post with request: {}", request);
        Long testUserId = 999L; // ID người dùng test
        return postService.createPost(request, testUserId);
    }
}
