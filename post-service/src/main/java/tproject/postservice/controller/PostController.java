package tproject.postservice.controller;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class PostController {


    private final PostService postService;

    @PostMapping("/create/light")
    public RestfulResponse<CreatedPostResponseDTO> createLightPost(@RequestBody CreatePostRequestDTO createPostRequestDTO,
                                                                   MultipartFile file,
                                                                   Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        CreatedPostResponseDTO createdPostResponseDTO = postService.createLightPost(createPostRequestDTO, userId, file);
        return RestfulResponse
                .success(createdPostResponseDTO, ResponseStatus.SUCCESS);

    }

    @GetMapping("/test")
    public RestfulResponse<String> test() {
        postService.test();
        return RestfulResponse
                .success("test", ResponseStatus.SUCCESS);
    }

    @GetMapping("/test-generate-presigned-url")
    public RestfulResponse<String> testGeneratePresignedUrl() {

        log.info("test generate presigned url");

        String presignedUrl = postService.genPreSignedUrl();
        return RestfulResponse
                .success(presignedUrl, ResponseStatus.SUCCESS);
    }

}
