package tproject.postservice.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tproject.postservice.dto.request.CreatePostRequestDTO;
import tproject.postservice.dto.response.CreatedPostResponseDTO;
import tproject.postservice.service.PostService;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class PostController {


    private final PostService postService;

    @PostMapping("/create/light")
    public RestfulResponse<CreatedPostResponseDTO> createLightPost(@RequestBody CreatePostRequestDTO createPostRequestDTO,
                                                                   MultipartFile media,
                                                                   Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        CreatedPostResponseDTO createdPostResponseDTO = postService.createLightPost(createPostRequestDTO, userId, media);
        return RestfulResponse
                .success(createdPostResponseDTO, ResponseStatus.SUCCESS);

    }

}
